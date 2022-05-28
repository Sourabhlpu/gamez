package sourabh.pal.cricket.search.presentation

sealed class SearchEvent{
    object PrepareForSearch: SearchEvent()
    data class QueryInput(val input: String): SearchEvent()
    data class distanceSelected(val distance: Double): SearchEvent()
    data class sportSelected(val sport: String): SearchEvent()
}
