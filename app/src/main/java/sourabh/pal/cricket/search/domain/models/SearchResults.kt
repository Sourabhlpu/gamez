package sourabh.pal.cricket.search.domain.models

import sourabh.pal.cricket.common.domain.model.player.Player

data class SearchResults(
    val players: List<Player>,
    val searchParameters: SearchParameters
)