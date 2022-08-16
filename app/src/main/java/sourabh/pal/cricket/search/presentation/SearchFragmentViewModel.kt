package sourabh.pal.cricket.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sourabh.pal.cricket.common.domain.NoMorePlayersException
import sourabh.pal.cricket.common.utils.DispatchersProvider
import sourabh.pal.cricket.common.utils.createExceptionHandler
import sourabh.pal.cricket.search.domain.usecases.GetSearchFilters
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val getSearchFilters: GetSearchFilters,
    private val dispatchersProvider: DispatchersProvider,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    val state: LiveData<SearchViewState> get() = _state
    private val _state: MutableLiveData<SearchViewState> = MutableLiveData()

    private val querySubject = BehaviorSubject.create<String>()
    private val sportSubject = BehaviorSubject.createDefault("")
    private val distanceSubject = BehaviorSubject.createDefault(0.0)

    private var currentPage = 0

    init {
        _state.value = SearchViewState()
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.PrepareForSearch -> prepareForSearch()
            else -> onSearchParametersUpdate(event)
        }
    }

    private fun onSearchParametersUpdate(event: SearchEvent) {
        when (event) {
            is SearchEvent.QueryInput -> updateQuery(event.input)
            is SearchEvent.sportSelected -> updateSportValue(event.sport)
            is SearchEvent.distanceSelected -> updateDistanceValue(event.distance)
        }
    }

    private fun updateSportValue(sport: String) {
        sportSubject.onNext(sport)
    }

    private fun updateDistanceValue(distance: Double) {
        distanceSubject.onNext(distance)
    }

    private fun updateQuery(input: String) {
        resetPagination()
        querySubject.onNext(input)

        if (input.isEmpty()) {
            setNoSearchQueryState()
        } else {
            setSearchingState()
        }
    }

    private fun setSearchingState() {
        _state.value = state.value!!.updateToSearching()
    }

    private fun setNoSearchQueryState() {
        _state.value = state.value!!.updateToNoSearchQuery()
    }

    private fun resetPagination() {
        currentPage = 0
    }

    private fun prepareForSearch() {
        loadFilterValues()
    }

    private fun loadFilterValues() {
        val exceptionHandler = createExceptionHandler(
            message = "Failed to get filter values!"
        )

        viewModelScope.launch(exceptionHandler) {
            withContext(dispatchersProvider.io()) {
                val (interestedSports, maxDistance) = getSearchFilters()
                updateStateWithFilterValues(interestedSports, maxDistance)
            }
        }
    }

    private fun updateStateWithFilterValues(interestedSports: List<String>, maxDistance: Double) {
        _state.value = state.value!!.updateToReadyToSearch(interestedSports, maxDistance)
    }

    private fun createExceptionHandler(message: String): CoroutineExceptionHandler {
        return viewModelScope.createExceptionHandler(message) {
            onFailure(it)
        }

    }

    private fun onFailure(throwable: Throwable) {
        _state.value = if (throwable is NoMorePlayersException) {
            state.value!!.updateToNoResultsAvailable()
        } else {
            state.value!!.updateToHasFailure(throwable)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}