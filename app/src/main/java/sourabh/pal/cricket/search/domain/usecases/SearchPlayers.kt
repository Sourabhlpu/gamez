package sourabh.pal.cricket.search.domain.usecases

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.functions.Function3
import io.reactivex.subjects.BehaviorSubject
import sourabh.pal.cricket.common.domain.repositories.PlayerRepository
import sourabh.pal.cricket.search.domain.models.SearchParameters
import sourabh.pal.cricket.search.domain.models.SearchResults
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchPlayers @Inject constructor(private val playerRepository: PlayerRepository) {

    private val combiningFunction: Function3<String, String, Double, SearchParameters>
    get() = Function3 { query, sport, distance ->
        SearchParameters(query, sport, distance)
    }
    operator fun invoke(
        querySubject: BehaviorSubject<String>,
        sportSubject: BehaviorSubject<String>,
        distanceSubject: BehaviorSubject<Double>
    ): Flowable<SearchResults> {

        val query = querySubject
            .debounce(500L, TimeUnit.MILLISECONDS)
            .map { it.trim() }
            .filter{ it.length >= 2}

        val sport = sportSubject.replaceUIEmptyValue()

        return Observable.combineLatest(query, sport, distanceSubject, combiningFunction)
            .toFlowable(BackpressureStrategy.LATEST)
            .switchMap { searchParameters: SearchParameters ->
                playerRepository.seachCachedPlayerBy(searchParameters)
            }

    }

    private fun BehaviorSubject<String>.replaceUIEmptyValue() = map{
        if(it == GetSearchFilters.NO_FILTER_SELECTED) "" else it
    }
}