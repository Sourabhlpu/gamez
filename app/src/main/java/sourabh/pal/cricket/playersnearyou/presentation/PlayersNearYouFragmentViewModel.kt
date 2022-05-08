package sourabh.pal.cricket.playersnearyou.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sourabh.pal.cricket.common.domain.NetworkException
import sourabh.pal.cricket.common.domain.NetworkUnavailableException
import sourabh.pal.cricket.common.domain.NoMorePlayersException
import sourabh.pal.cricket.common.domain.model.player.Player
import sourabh.pal.cricket.common.domain.pagination.Pagination
import sourabh.pal.cricket.common.presentation.Event
import sourabh.pal.cricket.common.presentation.model.mappers.UiPlayerMapper
import sourabh.pal.cricket.common.utils.DispatchersProvider
import sourabh.pal.cricket.common.utils.createExceptionHandler
import sourabh.pal.cricket.playersnearyou.domain.usescases.GetPlayers
import sourabh.pal.cricket.playersnearyou.domain.usescases.RequestNextPageOfPlayers
import javax.inject.Inject

@HiltViewModel
class PlayersNearYouFragmentViewModel @Inject constructor(
    private val getPlayers: GetPlayers,
    private val requestNextPageOfPlayers: RequestNextPageOfPlayers,
    private val uiPlayerMapper: UiPlayerMapper,
    private val dispatchersProvider: DispatchersProvider,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {
    companion object {
        const val UI_PAGE_SIZE = Pagination.DEFAULT_PAGE_SIZE
    }

    val state: LiveData<PlayerNearYouViewState> get() = _state
    var isLoadingMorePlayers: Boolean = false
    var isLastPage = false

    private val _state = MutableLiveData<PlayerNearYouViewState>()
    private var currentPage = 0

    init {
        _state.value = PlayerNearYouViewState()
        subscribeToPlayerUpdates()
    }


    fun onEvent(event: PlayersNearYouEvent) {
        when (event) {
            is PlayersNearYouEvent.RequireInitialPlayersList -> loadPlayers()
            is PlayersNearYouEvent.RequestMorePlayers -> loadNextPlayersPage()
        }
    }

    private fun loadPlayers() {
        if (_state.value!!.players.isEmpty()) {
            loadNextPlayersPage()
        }
    }

    private fun subscribeToPlayerUpdates() {
        getPlayers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onNewPlayersList(it) },
                { onFailure(it) }
            )
            .addTo(compositeDisposable)
    }

    private fun onNewPlayersList(players: List<Player>) {
        val playersNearYou = players.map { uiPlayerMapper.mapToView(it) }

        val currentList = _state.value!!.players
        val newPlayers = playersNearYou.subtract(currentList)
        val updateList = currentList + newPlayers
        _state.value = state.value!!.copy(loading = false, players = playersNearYou)
    }

    private fun loadNextPlayersPage() {
        isLoadingMorePlayers = true

        val errorMessage = "Failed to fetch nearby Players"
        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) { onFailure(it) }

        viewModelScope.launch(exceptionHandler) {
            val pagination = withContext(dispatchersProvider.io()) {
                requestNextPageOfPlayers(++currentPage)
            }
            onPaginationInfoObtained(pagination)
            isLoadingMorePlayers = false
        }
    }

    private fun onPaginationInfoObtained(pagination: Pagination) {
        currentPage = pagination.currentPage
        isLastPage = !pagination.canLoadMore
    }

    private fun onFailure(failure: Throwable) {
        when (failure) {
            is NetworkUnavailableException,
            is NetworkException -> {
                _state.value = state.value!!.copy(
                    loading = false,
                    failure = Event(failure)
                )
            }
            is NoMorePlayersException -> {
                _state.value = state.value!!.copy(
                    noMorePlayersNearby = false,
                    failure = Event(failure)
                )
            }
            else -> {
                _state.value = state.value!!.copy(
                    loading =  false,
                    failure = Event(failure)
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}