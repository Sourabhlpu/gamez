package sourabh.pal.cricket.common.domain.repositories

import io.reactivex.Flowable
import sourabh.pal.cricket.common.domain.model.sport.Sport
import sourabh.pal.cricket.common.domain.pagination.PaginatedSports

interface SportsRepository {
    fun getAllSports(): List<Sport>
    suspend fun storeSport(sports: List<Sport>)
    suspend fun requestSports(pageToLoad: Int, numberOfItems: Int): PaginatedSports
}