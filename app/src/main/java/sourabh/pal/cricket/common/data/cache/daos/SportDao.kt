package sourabh.pal.cricket.common.data.cache.daos

import androidx.room.*
import io.reactivex.Flowable
import sourabh.pal.cricket.common.data.cache.model.cachedplayer.CachedPlayerAggregate
import sourabh.pal.cricket.common.data.cache.model.cachedplayer.CachedPlayerWithDetails
import sourabh.pal.cricket.common.data.cache.model.cachedsport.CachedSport

@Dao
abstract class SportDao {

    @Transaction
    @Query("SELECT * FROM sports ORDER BY sportId DESC")
    abstract fun getAllSports(): List<CachedSport>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertSport(sport: CachedSport)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertSports(sports: List<CachedSport>)


}