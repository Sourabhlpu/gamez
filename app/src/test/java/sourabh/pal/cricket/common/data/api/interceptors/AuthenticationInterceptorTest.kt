package sourabh.pal.cricket.common.data.api.interceptors

import android.os.Build
import com.google.common.truth.Truth.assertThat
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.threeten.bp.Instant
import sourabh.pal.cricket.common.data.ApiConstants
import sourabh.pal.cricket.common.data.ApiParameters
import sourabh.pal.cricket.common.data.api.JsonReader
import sourabh.pal.cricket.common.data.preferences.Preferences

@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE, sdk = [Build.VERSION_CODES.P])
class AuthenticationInterceptorTest{
    private lateinit var preferences: Preferences
    private lateinit var mockWebServer: MockWebServer
    private lateinit var authenticationInterceptor: AuthenticationInterceptor
    private lateinit var okHttpClient: OkHttpClient

    private val endpointSeparator = "/"
    private val playersEndpointPath = endpointSeparator + ApiConstants.NEARBY_PLAYERS_ENDPOINT
    private val authEndpointPath = endpointSeparator + ApiConstants.AUTH_ENDPOINT
    private val validToken = "validToken"
    private val expiredToken = "expiredToken"

    @Before
    fun setup(){
        preferences = mock(Preferences::class.java)

        mockWebServer = MockWebServer()
        mockWebServer.start(8080)
        authenticationInterceptor = AuthenticationInterceptor(preferences)
        okHttpClient = OkHttpClient().newBuilder().addInterceptor(authenticationInterceptor).build()
    }

    @After
    fun teardown(){
        mockWebServer.shutdown()
    }

    @Test
    fun authenticationInterceptor_validToken(){
        //given
        `when`(preferences.getToken()).thenReturn(validToken)
        `when`(preferences.getTokenExpirationTime()).thenReturn(
            Instant.now().plusSeconds(3600).epochSecond
        )
        mockWebServer.dispatcher = getDispatcherForValidToken()

        //when
        okHttpClient.newCall(
            Request.Builder().url(mockWebServer.url(ApiConstants.NEARBY_PLAYERS_ENDPOINT)).build()
        ).execute()

        //then
        val request = mockWebServer.takeRequest()

        with(request){
            assertThat(method).isEqualTo("GET")
            assertThat(path).isEqualTo(playersEndpointPath)
            assertThat(getHeader(ApiParameters.AUTH_HEADER)).isEqualTo(ApiParameters.TOKEN_TYPE + validToken)
        }

    }

    @Test
    fun authenticationInterceptor_expiredToken(){
        //given
        `when`(preferences.getToken()).thenReturn(expiredToken)
        `when`(preferences.getTokenExpirationTime()).thenReturn(
            Instant.now().minusSeconds(3600).epochSecond
        )
        mockWebServer.dispatcher = getDispatcherForExpiredToken()

        //when
        okHttpClient.newCall(
            Request.Builder().url(mockWebServer.url(ApiConstants.NEARBY_PLAYERS_ENDPOINT)).build()
        ).execute()

        //then
        val tokenRequest = mockWebServer.takeRequest()
        val playersRequest = mockWebServer.takeRequest()

        with(tokenRequest){
            assertThat(method).isEqualTo("POST")
            assertThat(path).isEqualTo(authEndpointPath)
        }

        val inOrder = inOrder(preferences)

        inOrder.verify(preferences).getToken()
        inOrder.verify(preferences).putToken(validToken)

        verify(preferences, times(1)).getToken()
        verify(preferences, times(1)).putToken(validToken)
        verify(preferences, times(1)).getTokenExpirationTime()
        verify(preferences, times(1)).putTokenExpirationTime(anyLong())
        verify(preferences, times(1)).putTokenType(ApiParameters.TOKEN_TYPE.trim())
        verifyNoMoreInteractions(preferences)

        with(playersRequest) {
            assertThat(method).isEqualTo("GET")
            assertThat(path).isEqualTo(playersEndpointPath)
            assertThat(getHeader(ApiParameters.AUTH_HEADER)).isEqualTo(ApiParameters.TOKEN_TYPE + validToken)

        }

    }

    private fun getDispatcherForValidToken() = object: Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when(request.path){
                playersEndpointPath -> {MockResponse().setResponseCode(200)}
                else ->{MockResponse().setResponseCode(404)}
            }
        }
    }

    private fun getDispatcherForExpiredToken() = object: Dispatcher(){
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when(request.path){
                authEndpointPath -> {
                    MockResponse().setResponseCode(200).setBody(JsonReader.getJson("validToken.json"))
                }
                playersEndpointPath -> { MockResponse().setResponseCode(200) }
                else -> { MockResponse().setResponseCode(404) }
            }
        }
    }
}