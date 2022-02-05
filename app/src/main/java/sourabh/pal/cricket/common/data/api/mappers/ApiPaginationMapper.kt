package sourabh.pal.cricket.common.data.api.mappers

import sourabh.pal.cricket.common.data.api.model.ApiPagination
import sourabh.pal.cricket.common.domain.pagination.Pagination
import javax.inject.Inject

class ApiPaginationMapper @Inject constructor(): ApiMapper<ApiPagination, Pagination>  {
    override fun mapToDomain(apiEntity: ApiPagination): Pagination {
        return Pagination(
            currentPage = apiEntity.currentPage ?: 0,
            totalPages = apiEntity.totalPages ?: 0
        )
    }
}