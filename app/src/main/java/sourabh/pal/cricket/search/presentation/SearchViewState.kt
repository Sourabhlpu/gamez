package sourabh.pal.cricket.search.presentation

import sourabh.pal.cricket.common.presentation.Event
import sourabh.pal.cricket.common.presentation.model.UIPlayer

data class SearchViewState(
    val noSearchQuery: Boolean = true,
    val searchResults: List<UIPlayer> = emptyList(),
    val sportsFilter: Event<List<String>> = Event(emptyList()),
    val maxDistance: Event<Double> = Event(0.0),
    val searchingRemotely: Boolean = false,
    val noRemoteResults: Boolean = false,
    val failure: Event<Throwable>? = null
){
    fun updateToReadyToSearch(sports: List<String>, maxDistance: Double): SearchViewState{
        return copy(
            sportsFilter = Event(sports),
            maxDistance = Event(maxDistance)
        )
    }

    fun updateToNoSearchQuery(): SearchViewState{
        return copy(
            noSearchQuery = true,
            searchResults = emptyList(),
            noRemoteResults = false
        )
    }

    fun updateToSearching(): SearchViewState{
        return copy(
            noSearchQuery = false,
            searchingRemotely = false,
            noRemoteResults = false
        )
    }

    fun updateToSearchingRemotely(): SearchViewState{
        return copy(
            searchingRemotely = true,
            searchResults = emptyList()
        )
    }

    fun updateToHasSearchResults(players: List<UIPlayer>): SearchViewState{
        return copy(
            noSearchQuery = false,
            searchResults = players,
            searchingRemotely = false,
            noRemoteResults = false

        )
    }

    fun updateToNoResultsAvailable(): SearchViewState {
        return copy(searchingRemotely = false, noRemoteResults = true)
    }

    fun updateToHasFailure(throwable: Throwable): SearchViewState {
        return copy(failure = Event(throwable))
    }
}