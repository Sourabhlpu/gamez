package sourabh.pal.cricket.search.domain.usecases

import sourabh.pal.cricket.common.domain.repositories.PlayerRepository
import sourabh.pal.cricket.common.domain.repositories.SportsRepository
import sourabh.pal.cricket.search.domain.models.SearchFilters
import javax.inject.Inject

class GetSearchFilters @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val sportsRepository: SportsRepository
) {

    suspend operator fun invoke(): SearchFilters {
        val sports = sportsRepository.getAllSports()
            .map {
                it.name.lowercase()
            }
        val maxDistance = playerRepository.getMaxDistance()
        return SearchFilters(
            sports,
            maxDistance
        )
    }
}