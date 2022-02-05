package sourabh.pal.cricket.common.data.api.mappers

import sourabh.pal.cricket.common.data.api.model.player.ApiLocation
import sourabh.pal.cricket.common.domain.model.player.details.DEFAULT_LAT
import sourabh.pal.cricket.common.domain.model.player.details.DEFAULT_LONGITUDE
import sourabh.pal.cricket.common.domain.model.player.details.Location
import javax.inject.Inject

class ApiLocationMapper @Inject constructor() : ApiMapper<ApiLocation?, Location> {
    override fun mapToDomain(apiEntity: ApiLocation?): Location {
        return Location(
            lat = apiEntity?.lat ?: DEFAULT_LAT,
            longitude = apiEntity?.longitude ?: DEFAULT_LONGITUDE
        )
    }
}