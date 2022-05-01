package sourabh.pal.cricket.common.presentation.model.mappers

import sourabh.pal.cricket.common.domain.model.player.Player
import sourabh.pal.cricket.common.presentation.model.UIPlayer
import javax.inject.Inject

class UiPlayerMapper @Inject constructor(): UiMapper<Player, UIPlayer>{
    override fun mapToView(input: Player): UIPlayer {
        return UIPlayer(
            id = input.id,
            name = input.username,
            //TODO add photo from domain
        )
    }
}