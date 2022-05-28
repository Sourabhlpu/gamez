package sourabh.pal.cricket.common.data

import io.reactivex.Flowable
import sourabh.pal.cricket.common.data.api.GameApi
import sourabh.pal.cricket.common.data.api.mappers.ApiPaginationMapper
import sourabh.pal.cricket.common.data.api.mappers.ApiPlayerDetailsMapper
import sourabh.pal.cricket.common.data.cache.Cache
import sourabh.pal.cricket.common.data.cache.model.cachedplayer.CachedPlayerAggregate
import sourabh.pal.cricket.common.data.cache.model.cachedsport.CachedSport
import sourabh.pal.cricket.common.domain.model.player.Player
import sourabh.pal.cricket.common.domain.model.player.details.PlayerWithDetails
import sourabh.pal.cricket.common.domain.pagination.PaginatedPlayers
import sourabh.pal.cricket.common.domain.repositories.PlayerRepository
import javax.inject.Inject

private const val MAX_DISTANCE = 100.0

class PlayerRepositoryIml @Inject constructor(
private val api: GameApi,
private val cache: Cache,
private val apiPlayerMapper: ApiPlayerDetailsMapper,
private val apiPaginationMapper: ApiPaginationMapper
) : PlayerRepository {
    private val postcode = "07097"
    private val maxDistanceMiles = 100

    override fun getNearbyPlayers(): Flowable<List<Player>> {
        return cache.getAllPlayers()
            .distinctUntilChanged()
            .map { playersList -> playersList.map { it.player.toPlayerDomain() } }
    }

    override suspend fun requestMorePlayers(pageToLoad: Int, numberOfItems: Int): PaginatedPlayers {
    val(apiPlayers, apiPagination) = api.getNearbyPlayers(
            pageToLoad,
            numberOfItems,
            postcode,
            maxDistanceMiles
        )

        return PaginatedPlayers(
            apiPlayers?.map { apiPlayerMapper.mapToDomain(it) }.orEmpty(),
            apiPaginationMapper.mapToDomain(apiPagination)
        )
    }

    override suspend fun storePlayers(players: List<PlayerWithDetails>) {
        cache.storePlayers(players.map { CachedPlayerAggregate.fromDomain(it) })
    }

    override fun getMaxDistance(): Double {
        return MAX_DISTANCE
    }
}