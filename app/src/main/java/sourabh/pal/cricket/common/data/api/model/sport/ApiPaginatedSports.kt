package sourabh.pal.cricket.common.data.api.model.sport

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import sourabh.pal.cricket.common.data.api.model.ApiPagination

@JsonClass(generateAdapter = true)
data class ApiPaginatedSports(
    @field:Json(name = "animals") val animals: List<ApiSport>?,
    @field:Json(name = "pagination") val pagination: ApiPagination?
) {
}