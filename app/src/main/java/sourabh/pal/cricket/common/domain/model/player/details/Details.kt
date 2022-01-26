package sourabh.pal.cricket.common.domain.model.player.details

import sourabh.pal.cricket.common.domain.model.sport.Sport

data class Details(
    val contact: Contact,
    val location: Location,
    val interestedSports: List<Sport>
)
