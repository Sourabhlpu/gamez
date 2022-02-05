package sourabh.pal.cricket.common.data.api.model.player

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import sourabh.pal.cricket.common.data.api.model.ApiPagination

@JsonClass(generateAdapter = true)
data class ApiPaginatedPlayers(
    @field:Json(name = "animals") val animals: List<ApiPlayer>?,
    @field:Json(name = "pagination") val pagination: ApiPagination?
)


