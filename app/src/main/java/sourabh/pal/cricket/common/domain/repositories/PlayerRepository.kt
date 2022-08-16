package sourabh.pal.cricket.common.domain.repositories

import io.reactivex.Flowable
import sourabh.pal.cricket.common.domain.model.player.Player
import sourabh.pal.cricket.common.domain.model.player.details.PlayerWithDetails
import sourabh.pal.cricket.common.domain.pagination.PaginatedPlayers
import sourabh.pal.cricket.search.domain.models.SearchParameters
import sourabh.pal.cricket.search.domain.models.SearchResults

interface PlayerRepository {
fun getNearbyPlayers(): Flowable<List<Player>>
suspend fun requestMorePlayers(pageToLoad: Int, numberOfItems: Int): PaginatedPlayers
suspend fun storePlayers(players: List<PlayerWithDetails>)
fun getMaxDistance(): Double
fun searchCachedPlayerBy(searchParameters: SearchParameters): Flowable<SearchResults>
}