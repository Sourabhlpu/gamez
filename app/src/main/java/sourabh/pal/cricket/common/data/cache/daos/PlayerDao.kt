package sourabh.pal.cricket.common.data.cache.daos

import androidx.room.*
import io.reactivex.Flowable
import sourabh.pal.cricket.common.data.cache.model.cachedplayer.CachedPlayerAggregate
import sourabh.pal.cricket.common.data.cache.model.cachedplayer.CachedPlayerWithDetails
import sourabh.pal.cricket.common.data.cache.model.cachedsport.CachedSport

@Dao
abstract class PlayerDao {

    @Transaction
    @Query("SELECT * FROM players ORDER BY playerId DESC")  //TODO: 2 Need to add distance to player object
    abstract fun getAllPlayers(): Flowable<List<CachedPlayerAggregate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPlayerAggregate(
        player: CachedPlayerWithDetails,
        sport: List<CachedSport>
    )

    suspend fun insertPlayerWithDetails(playerAggregates: List<CachedPlayerAggregate>) {
        for (playerAggregate in playerAggregates) {
            insertPlayerAggregate(
                playerAggregate.player,
                playerAggregate.sports,
            )
        }
    }

    @Transaction
    @Query("""
    SELECT * FROM players
      WHERE username LIKE '%' || :name || '%' AND
      AGE LIKE '%' || :age || '%'
      AND type LIKE '%' || :type || '%'
  """)
    abstract fun searchPlayerBy(name: String, interestedSport: String, max: Any): Flowable<List<CachedPlayerAggregate>>
}