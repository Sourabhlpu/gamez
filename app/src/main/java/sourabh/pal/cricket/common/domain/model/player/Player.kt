package sourabh.pal.cricket.common.domain.model.player

import org.threeten.bp.LocalDateTime


data class Player(
    val id: Long,
    val username: String,
    val createdAt: LocalDateTime
)
