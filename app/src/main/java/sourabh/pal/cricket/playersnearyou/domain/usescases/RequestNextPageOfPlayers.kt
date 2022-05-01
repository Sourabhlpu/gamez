package sourabh.pal.cricket.playersnearyou.domain.usescases

import sourabh.pal.cricket.common.domain.NoMorePlayersException
import sourabh.pal.cricket.common.domain.pagination.Pagination
import sourabh.pal.cricket.common.domain.repositories.PlayerRepository
import javax.inject.Inject

class RequestNextPageOfPlayers @Inject constructor(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(
        pageToLoad: Int,
        pageSize: Int = Pagination.DEFAULT_PAGE_SIZE
    ): Pagination {
        val(players, pagination) = repository.requestMorePlayers(pageToLoad, pageSize)

        if(players.isEmpty()){
            throw NoMorePlayersException("No Players Nearby:(")
        }
        repository.storePlayers(players)

        return pagination
    }
}