package sourabh.pal.cricket.common.data.api

import androidx.core.view.ViewCompat
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import sourabh.pal.cricket.common.data.ApiConstants
import sourabh.pal.cricket.common.domain.model.auth.IAuthUser

interface GameApi {

    @POST(ApiConstants.SIGNUP_ENDPOINT)
    suspend fun signup(@Body user: IAuthUser)

    @POST(ApiConstants.LOGIN_ENDPOINT)
    suspend fun login(@Body user: IAuthUser)
}