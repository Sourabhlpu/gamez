package sourabh.pal.cricket.playersnearyou.domain.usescases

import sourabh.pal.cricket.common.domain.repositories.PlayerRepository
import javax.inject.Inject

class GetPlayers @Inject constructor(private val repository: PlayerRepository) {
    operator fun invoke() = repository.getNearbyPlayers()
        .filter{it.isNotEmpty()}
}