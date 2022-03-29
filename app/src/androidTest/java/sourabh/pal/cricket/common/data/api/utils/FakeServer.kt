package sourabh.pal.cricket.common.data.api.utils

import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import sourabh.pal.cricket.common.data.ApiConstants
import java.io.IOException
import java.io.InputStream

class FakeServer {
    private val mockWebServer = MockWebServer()

    private val endpointSeparator = "/"
    private val playersEndpointPath = endpointSeparator + ApiConstants.NEARBY_PLAYERS_ENDPOINT
    private val notFoundResponse = MockResponse().setResponseCode(404)

    val baseEndpoint
        get() = mockWebServer.url(endpointSeparator)

    fun start() {
        mockWebServer.start(8000)
    }

    fun setHappyPathDispatcher() {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                val path = request.path ?: return notFoundResponse

                return with(path) {
                    when {
                        startsWith(playersEndpointPath) -> {
                            MockResponse().setResponseCode(200).setBody(getJson("players.json"))
                        }
                        else -> {
                            notFoundResponse
                        }
                    }
                }
            }
        }
    }

    fun shutdown() {
        mockWebServer.shutdown()
    }

    private fun getJson(path: String): String {
        return try {
            val context = InstrumentationRegistry.getInstrumentation().context
            val jsonStream: InputStream = context.assets.open("networkresponses/$path")
            String(jsonStream.readBytes())
        } catch (exception: IOException) {
            throw exception
        }
    }
}