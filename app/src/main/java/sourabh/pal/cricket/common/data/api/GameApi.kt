package sourabh.pal.cricket.common.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import sourabh.pal.cricket.common.data.ApiConstants
import sourabh.pal.cricket.common.data.ApiParameters
import sourabh.pal.cricket.common.data.api.model.ApiToken
import sourabh.pal.cricket.common.data.api.model.player.ApiPaginatedPlayers
import sourabh.pal.cricket.common.data.api.model.sport.ApiPaginatedSports
import sourabh.pal.cricket.common.domain.model.auth.IAuthUser

interface GameApi {

    @POST(ApiConstants.SIGNUP_ENDPOINT)
    suspend fun signup(@Body user: IAuthUser): ApiToken

    @POST(ApiConstants.LOGIN_ENDPOINT)
    suspend fun login(@Body user: IAuthUser): ApiToken

    @GET(ApiConstants.NEARBY_PLAYERS_ENDPOINT)
    suspend fun getNearbyPlayers(
        @Query(ApiParameters.PAGE) pageToLoad: Int,
        @Query(ApiParameters.LIMIT) pageSize: Int,
        @Query(ApiParameters.LOCATION) postcode: String,
        @Query(ApiParameters.DISTANCE) maxDistance: Int
    ): ApiPaginatedPlayers

    @GET(ApiConstants.ALL_SPORTS_ENDPOINT)
    suspend fun getAllSports(
        @Query(ApiParameters.PAGE) pageToLoad: Int,
        @Query(ApiParameters.LIMIT) pageSize: Int,
    ): ApiPaginatedSports
}