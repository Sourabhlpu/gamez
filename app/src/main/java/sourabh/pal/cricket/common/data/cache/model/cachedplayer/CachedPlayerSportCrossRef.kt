package sourabh.pal.cricket.common.data.cache.model.cachedplayer

import androidx.room.Entity
import androidx.room.Index

@Entity(primaryKeys = ["playerId", "sportId"], indices = [Index("sportId")])
data class CachedPlayerSportCrossRef(
val playerId: Long,
val sportId: Long
)