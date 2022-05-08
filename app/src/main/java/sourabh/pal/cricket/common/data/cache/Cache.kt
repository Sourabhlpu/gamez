package sourabh.pal.cricket.common.data.cache

import io.reactivex.Flowable
import sourabh.pal.cricket.common.data.cache.model.cachedplayer.CachedPlayerAggregate
import sourabh.pal.cricket.common.data.cache.model.cachedsport.CachedSport
import java.util.concurrent.Flow

interface Cache {

    suspend fun storePlayers(players: List<CachedPlayerAggregate>)

    fun getAllPlayers(): Flowable<List<CachedPlayerAggregate>>

    suspend fun storeSports(sports: List<CachedSport>)

    suspend fun getAllSports(): Flowable<List<CachedSport>>

}