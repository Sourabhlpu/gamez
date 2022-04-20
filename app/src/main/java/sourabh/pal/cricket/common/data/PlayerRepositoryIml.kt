package sourabh.pal.cricket.common.data

import io.reactivex.Flowable
import sourabh.pal.cricket.common.data.api.GameApi
import sourabh.pal.cricket.common.data.api.mappers.ApiPaginationMapper
import sourabh.pal.cricket.common.data.api.mappers.ApiPlayerDetailsMapper
import sourabh.pal.cricket.common.data.cache.Cache
import sourabh.pal.cricket.common.domain.model.player.Player
import sourabh.pal.cricket.common.domain.model.player.details.PlayerWithDetails
import sourabh.pal.cricket.common.domain.pagination.PaginatedPlayers
import sourabh.pal.cricket.common.domain.repositories.PlayerRepository
import javax.inject.Inject

class PlayerRepositoryIml @Inject constructor(
private val api: GameApi,
private val cache: Cache,
private val apiPlayerMapper: ApiPlayerDetailsMapper,
private val apiPaginationMapper: ApiPaginationMapper
) : PlayerRepository {
    private val postcode = "07097"
    private val maxDistanceMiles = 100
    override fun getNearbyPlayers(): Flowable<List<Player>> {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }
}