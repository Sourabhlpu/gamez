package sourabh.pal.cricket.playersnearyou.presentation

import sourabh.pal.cricket.common.presentation.Event
import sourabh.pal.cricket.common.presentation.model.UIPlayer

data class PlayerNearYouViewState(
    val loading: Boolean = true,
    val players: List<UIPlayer> = emptyList(),
    val noMorePlayersNearby: Boolean = false,
    val failure: Event<Throwable>? = null
)