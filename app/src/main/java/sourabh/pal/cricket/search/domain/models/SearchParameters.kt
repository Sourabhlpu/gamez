package sourabh.pal.cricket.search.domain.models

data class SearchParameters(
    val name: String,
    val interestedSport: String,
    val maxDistance: Double
)
