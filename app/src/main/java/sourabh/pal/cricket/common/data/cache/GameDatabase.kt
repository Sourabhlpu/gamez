package sourabh.pal.cricket.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import sourabh.pal.cricket.common.data.cache.daos.PlayerDao
import sourabh.pal.cricket.common.data.cache.daos.SportDao
import sourabh.pal.cricket.common.data.cache.model.cachedplayer.CachedPlayerSportCrossRef
import sourabh.pal.cricket.common.data.cache.model.cachedplayer.CachedPlayerWithDetails
import sourabh.pal.cricket.common.data.cache.model.cachedsport.CachedSport

@Database(
    entities = [
        CachedSport::class,
        CachedPlayerWithDetails::class,
        CachedPlayerSportCrossRef::class,
    ],
    version = 1
)
abstract class GameDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun sportDao(): SportDao
}