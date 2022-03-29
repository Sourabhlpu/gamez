package sourabh.pal.cricket.common.data

import io.reactivex.Flowable
import sourabh.pal.cricket.common.data.api.GameApi
import sourabh.pal.cricket.common.data.cache.Cache
import sourabh.pal.cricket.common.domain.model.player.Player
import sourabh.pal.cricket.common.domain.model.player.details.PlayerWithDetails
import sourabh.pal.cricket.common.domain.pagination.PaginatedPlayers
import sourabh.pal.cricket.common.domain.repositories.PlayerRepository
import javax.inject.Inject

class PlayerRepositoryIml @Inject constructor(
private val api: GameApi,
private val cache: Cache
) : PlayerRepository {
    override fun getNearbyPlayers(): Flowable<List<Player>> {
        TODO("Not yet implemented")
    }

    override suspend fun requestMorePlayers(pageToLoad: Int, numberOfItems: Int): PaginatedPlayers {
        TODO("Not yet implemented")
    }

    override suspend fun storePlayers(players: List<PlayerWithDetails>) {
        TODO("Not yet implemented")
    }
}