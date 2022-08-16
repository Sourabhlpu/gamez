package sourabh.pal.cricket.search.domain.usecases

import sourabh.pal.cricket.common.domain.repositories.PlayerRepository
import sourabh.pal.cricket.common.domain.repositories.SportsRepository
import sourabh.pal.cricket.search.domain.models.SearchFilters
import javax.inject.Inject

class GetSearchFilters @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val sportsRepository: SportsRepository
) {

    companion object {
        const val NO_FILTER_SELECTED = "Any"
    }

    suspend operator fun invoke(): SearchFilters {
        val sports = listOf(NO_FILTER_SELECTED) + sportsRepository.getAllSports()
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