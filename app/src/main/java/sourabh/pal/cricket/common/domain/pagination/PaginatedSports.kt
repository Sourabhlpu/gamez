package sourabh.pal.cricket.common.domain.pagination

import sourabh.pal.cricket.common.domain.model.sport.Sport

data class PaginatedSports(
    val sports: List<Sport>,
    val pagination: Pagination
)