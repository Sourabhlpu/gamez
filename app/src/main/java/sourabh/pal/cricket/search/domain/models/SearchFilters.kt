package sourabh.pal.cricket.search.domain.models

data class SearchFilters(
    val interestedSports: List<String>,
    val maxDistance: Double
)