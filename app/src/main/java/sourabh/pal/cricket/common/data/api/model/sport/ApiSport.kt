package sourabh.pal.cricket.common.data.api.model.sport

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ApiSport(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "type") val type: String?,
    @field:Json(name = "playersInTeam") val playersInTeam: Int?,
)
