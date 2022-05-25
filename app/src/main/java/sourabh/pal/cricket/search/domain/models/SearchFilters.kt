package sourabh.pal.cricket.search.domain.models

class SearchFilters(
    val interestedSports: List<String>,
    val distanceRange: List<Pair<Double, Double>>
)