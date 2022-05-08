package sourabh.pal.cricket.common.data.cache

import io.reactivex.Flowable
import sourabh.pal.cricket.common.data.cache.daos.PlayerDao
import sourabh.pal.cricket.common.data.cache.daos.SportDao
import sourabh.pal.cricket.common.data.cache.model.cachedplayer.CachedPlayerAggregate
import sourabh.pal.cricket.common.data.cache.model.cachedsport.CachedSport
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val sportDao: SportDao,
    private val playerDao: PlayerDao
): Cache {
    override suspend fun storePlayers(players: List<CachedPlayerAggregate>) {
        playerDao.insertPlayerWithDetails(players)
    }

    override  fun getAllPlayers(): Flowable<List<CachedPlayerAggregate>> {
        return playerDao.getAllPlayers()
    }

    override suspend fun storeSports(sports: List<CachedSport>) {
        sportDao.insertSports(sports)
    }

    override suspend fun getAllSports(): Flowable<List<CachedSport>> {
        return sportDao.getAllSports()
    }
}