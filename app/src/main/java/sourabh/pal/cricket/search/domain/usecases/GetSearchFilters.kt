package sourabh.pal.cricket.search.domain.usecases

import sourabh.pal.cricket.common.domain.repositories.PlayerRepository
import sourabh.pal.cricket.search.domain.models.SearchFilters
import javax.inject.Inject

class GetSearchFilters @Inject constructor(private val playerRepository: PlayerRepository) {

    suspend operator fun invoke(): SearchFilters{

    }
}