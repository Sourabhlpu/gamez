package sourabh.pal.cricket.common.data

import io.reactivex.Flowable
import sourabh.pal.cricket.common.data.api.GameApi
import sourabh.pal.cricket.common.data.api.mappers.ApiPaginationMapper
import sourabh.pal.cricket.common.data.api.mappers.ApiSportMapper
import sourabh.pal.cricket.common.data.cache.Cache
import sourabh.pal.cricket.common.data.cache.model.cachedsport.CachedSport
import sourabh.pal.cricket.common.domain.model.sport.Sport
import sourabh.pal.cricket.common.domain.pagination.PaginatedSports
import sourabh.pal.cricket.common.domain.repositories.SportsRepository
import javax.inject.Inject

class SportsRepositoryIml @Inject constructor(
    private val gameApi: GameApi,
    private val cache: Cache,
    private val apiSportMapper: ApiSportMapper,
    private val apiPaginationMapper: ApiPaginationMapper

): SportsRepository {

    override fun getAllSports(): Flowable<List<Sport>> {
        return cache.getAllSports()
            .distinctUntilChanged()
            .map { sportsList -> sportsList.map { it.toDomain() } }
    }

    override suspend fun storeSport(sports: List<Sport>) {
        cache.storeSports(sports.map { CachedSport.fromDomain(it) })
    }

    override suspend fun requestSports(pageToLoad: Int, numberOfItems: Int): PaginatedSports {
        val(sports, pagination) = gameApi.getAllSports(pageToLoad, numberOfItems)

        return PaginatedSports(
            sports?.map { apiSportMapper.mapToDomain(it) }.orEmpty(),
            apiPaginationMapper.mapToDomain(pagination)
        )
    }
}