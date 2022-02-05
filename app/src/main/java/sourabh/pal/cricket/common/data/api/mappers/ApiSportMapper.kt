package sourabh.pal.cricket.common.data.api.mappers

import sourabh.pal.cricket.common.data.api.model.sport.ApiSport
import sourabh.pal.cricket.common.domain.model.sport.Sport
import sourabh.pal.cricket.common.domain.model.sport.SportType
import java.util.*
import javax.inject.Inject

class ApiSportMapper @Inject constructor(): ApiMapper<ApiSport, Sport> {
    override fun mapToDomain(apiEntity: ApiSport): Sport {
        return Sport(
            id = apiEntity?.id ?: throw MappingException("Sport id cannot be null"),
            name = apiEntity?.name.orEmpty(),
            sportType = parseSportType(apiEntity?.type),
            playersInTeam = apiEntity?.playersInTeam ?: 1
        )
    }

     private fun parseSportType(sportType: String?): SportType{
         if(sportType.isNullOrEmpty()) return SportType.UNKNOWN
         return SportType.valueOf(sportType.toUpperCase(Locale.ROOT))
    }
}

