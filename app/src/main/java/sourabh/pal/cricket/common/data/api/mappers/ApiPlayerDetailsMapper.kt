package sourabh.pal.cricket.common.data.api.mappers

import sourabh.pal.cricket.common.data.api.model.player.ApiPlayer
import sourabh.pal.cricket.common.data.api.model.player.ApiPlayerDetails
import sourabh.pal.cricket.common.domain.model.player.details.PlayerDetails
import sourabh.pal.cricket.common.domain.model.player.details.PlayerWithDetails
import sourabh.pal.cricket.utils.DateTimeUtils
import javax.inject.Inject

class ApiPlayerDetailsMapper @Inject constructor(
    private val contactMapper: ApiContactMapper,
    private val locationMapper: ApiLocationMapper,
    private val sportMapper: ApiSportMapper
): ApiMapper<ApiPlayer, PlayerWithDetails> {

    override fun mapToDomain(apiEntity: ApiPlayer): PlayerWithDetails {
        return PlayerWithDetails(
            id = apiEntity.id ?: throw MappingException("Player ID cannot be null"),
            username = apiEntity.username.orEmpty(),
            createdAt = DateTimeUtils.parse(apiEntity.createdAt.orEmpty()),
            details = parsePlayerDetails(apiEntity.details)
        )
    }

    private fun parsePlayerDetails(apiPlayerDetails: ApiPlayerDetails): PlayerDetails {
        return PlayerDetails(
            contact = contactMapper.mapToDomain(apiPlayerDetails.contact),
            location = locationMapper.mapToDomain(apiPlayerDetails.location),
            interestedSports = apiPlayerDetails.interestedSports.orEmpty().map { sportMapper.mapToDomain(it) }
        )
    }
}