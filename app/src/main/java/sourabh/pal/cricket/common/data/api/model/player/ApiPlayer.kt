package sourabh.pal.cricket.common.data.api.model.player

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import sourabh.pal.cricket.common.data.api.model.sport.ApiSport

@JsonClass(generateAdapter = true)
data class ApiPlayer(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "username")  val username: String?,
    @field:Json(name = "createdAt") val createdAt: Long,
    @field:Json(name = "details") val details: ApiPlayerDetails
)

@JsonClass(generateAdapter = true)
data class ApiPlayerDetails(
    @field:Json(name = "contact") val contact: ApiContact?,
    @field:Json(name = "location") val location: ApiLocation?,
    @field:Json(name = "interestedSports") val interestedSports: List<ApiSport>?,
)

@JsonClass(generateAdapter = true)
data class ApiContact(
    @field:Json(name = "email") val email: String?,
    @field:Json(name = "phone") val phone: String?,
)

@JsonClass(generateAdapter = true)
data class ApiLocation(
    @field:Json(name = "lat") val lat: Double?,
    @field:Json(name = "longitude") val longitude: Double?,
)
