package sourabh.pal.cricket.common.data

object ApiConstants {
    const val BASE_ENDPOINT = "https://run.mocky.io/v3/"  //Todo:1 add base endpoint when api is ready
    const val SIGNUP_ENDPOINT = "signup"
    const val LOGIN_ENDPOINT = "login"
    const val AUTH_ENDPOINT = "token"
    const val NEARBY_PLAYERS_ENDPOINT = "632b2e7c-51ad-473d-855f-8727f9bab29c" //TODO:2 make it nearby when using real api
    const val ALL_SPORTS_ENDPOINT = "sports"

}

object ApiParameters {
    const val TOKEN_TYPE = "Bearer "
    const val AUTH_HEADER = "Authorization"
    const val GRANT_TYPE_KEY = "grant_type"
    const val GRANT_TYPE_VALUE = "client_credentials"


    const val PAGE = "page"
    const val LIMIT = "limit"
    const val LOCATION = "location"
    const val DISTANCE = "distance"

}