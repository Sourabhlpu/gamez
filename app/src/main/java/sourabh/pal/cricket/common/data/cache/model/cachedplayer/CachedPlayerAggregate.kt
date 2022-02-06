package sourabh.pal.cricket.common.data.cache.model.cachedplayer

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import sourabh.pal.cricket.common.data.cache.model.cachedsport.CachedSport
import sourabh.pal.cricket.common.domain.model.player.details.PlayerWithDetails

data class CachedPlayerAggregate(
    @Embedded
    val player: CachedPlayerWithDetails,

    @Relation(
        parentColumn = "playerId",
        entityColumn = "sportId",
        associateBy = Junction(CachedPlayerSportCrossRef::class)
    )
    val sports: List<CachedSport>
){
    companion object{
        fun fromDomain(playerWithDetails: PlayerWithDetails): CachedPlayerAggregate{
            val interestedSports = playerWithDetails.details.interestedSports
            return CachedPlayerAggregate(
                player = CachedPlayerWithDetails.fomDomain(playerWithDetails),
                sports = interestedSports.map { CachedSport.fromDomain(it) }
            )
        }
    }
}