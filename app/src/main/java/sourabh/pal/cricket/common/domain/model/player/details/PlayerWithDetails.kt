package sourabh.pal.cricket.common.domain.model.player.details

import org.threeten.bp.LocalDateTime

class PlayerWithDetails(
    val id: Long,
    val username: String,
    val createdAt: LocalDateTime,
    val details: Details
)