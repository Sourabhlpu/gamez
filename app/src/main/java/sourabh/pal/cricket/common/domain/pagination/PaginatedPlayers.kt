package sourabh.pal.cricket.common.domain.pagination

import sourabh.pal.cricket.common.domain.model.player.Player

data class PaginatedPlayers(
    val players: List<Player>,
    val pagination: Pagination
)