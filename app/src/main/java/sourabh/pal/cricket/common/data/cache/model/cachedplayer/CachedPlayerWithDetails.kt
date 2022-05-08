package sourabh.pal.cricket.common.data.cache.model.cachedplayer

import androidx.room.Entity
import androidx.room.PrimaryKey
import sourabh.pal.cricket.common.data.cache.model.cachedsport.CachedSport
import sourabh.pal.cricket.common.domain.model.player.Player
import sourabh.pal.cricket.common.domain.model.player.details.Contact
import sourabh.pal.cricket.common.domain.model.player.details.Location
import sourabh.pal.cricket.common.domain.model.player.details.PlayerDetails
import sourabh.pal.cricket.common.domain.model.player.details.PlayerWithDetails
import sourabh.pal.cricket.utils.DateTimeUtils

@Entity(tableName = "players")
data class CachedPlayerWithDetails(
    @PrimaryKey
    val playerId: Long,
    val username: String,
    val createdAt: String,
    val email: String,
    val phone: String,
    val lat: Double,
    val longitude: Double
){
    companion object{
        fun fomDomain(domainModel: PlayerWithDetails): CachedPlayerWithDetails {
            val details = domainModel.details
            return CachedPlayerWithDetails(
                playerId = domainModel.id,
                username = domainModel.username,
                createdAt = domainModel.createdAt.toString(),
                email = details.contact.email,
                phone = details.contact.phone,
                lat = details.location.lat,
                longitude = details.location.longitude
            )
        }
    }

    fun toDomain(sports: List<CachedSport>): PlayerWithDetails{
        return PlayerWithDetails(
            id = playerId,
            username = username,
            createdAt = DateTimeUtils.parse(createdAt),
            details = mapDetails(sports)
        )
    }

    fun toPlayerDomain(): Player{
        return Player(
            id = playerId,
            username = username,
            createdAt = DateTimeUtils.parse(createdAt)
        )
    }

    private fun mapDetails(sports: List<CachedSport>): PlayerDetails {
        return PlayerDetails(
            contact = Contact(email, phone),
            location = Location(lat, longitude),
            interestedSports = sports.map { it.toDomain() }
        )
    }
}