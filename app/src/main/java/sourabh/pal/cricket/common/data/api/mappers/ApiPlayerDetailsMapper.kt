package sourabh.pal.cricket.common.data.api.mappers

import sourabh.pal.cricket.common.data.api.model.player.ApiPlayerDetails
import sourabh.pal.cricket.common.domain.model.player.details.PlayerDetails
import javax.inject.Inject

class ApiPlayerDetailsMapper @Inject constructor(
    private val contactMapper: ApiContactMapper,
    private val locationMapper: ApiLocationMapper,
    private val sportMapper: ApiSportMapper
): ApiMapper<ApiPlayerDetails, PlayerDetails> {

    override fun mapToDomain(apiEntity: ApiPlayerDetails): PlayerDetails {
        return PlayerDetails(
            contact = contactMapper.mapToDomain(apiEntity.contact),
            location = locationMapper.mapToDomain(apiEntity.location),
            interestedSports = apiEntity.interestedSports.orEmpty().map { sportMapper.mapToDomain(it) }
        )
    }
}