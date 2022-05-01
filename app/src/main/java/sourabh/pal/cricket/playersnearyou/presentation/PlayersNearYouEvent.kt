package sourabh.pal.cricket.playersnearyou.presentation

sealed class PlayersNearYouEvent {
    object RequireInitialPlayersList: PlayersNearYouEvent()
    object RequestMorePlayers: PlayersNearYouEvent()
}