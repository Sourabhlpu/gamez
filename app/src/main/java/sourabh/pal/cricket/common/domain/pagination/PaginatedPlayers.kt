package sourabh.pal.cricket.common.domain.pagination

import sourabh.pal.cricket.common.domain.model.player.details.PlayerWithDetails

data class PaginatedPlayers(
    val players: List<PlayerWithDetails>,
    val pagination: Pagination
)