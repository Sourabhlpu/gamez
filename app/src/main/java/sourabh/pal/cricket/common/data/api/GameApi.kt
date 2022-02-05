package sourabh.pal.cricket.common.data.api

import retrofit2.http.Body
import retrofit2.http.POST
import sourabh.pal.cricket.common.data.ApiConstants
import sourabh.pal.cricket.common.data.api.model.ApiToken
import sourabh.pal.cricket.common.domain.model.auth.IAuthUser

interface GameApi {

    @POST(ApiConstants.SIGNUP_ENDPOINT)
    suspend fun signup(@Body user: IAuthUser): ApiToken

    @POST(ApiConstants.LOGIN_ENDPOINT)
    suspend fun login(@Body user: IAuthUser): ApiToken
}