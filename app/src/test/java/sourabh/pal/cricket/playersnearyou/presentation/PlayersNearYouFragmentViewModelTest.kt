package sourabh.pal.cricket.playersnearyou.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.disposables.CompositeDisposable
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import sourabh.pal.cricket.RxImmediateSchedulerRule
import sourabh.pal.cricket.TestCoroutineRule
import sourabh.pal.cricket.common.data.FakeRepository
import sourabh.pal.cricket.common.domain.NetworkException
import sourabh.pal.cricket.common.presentation.Event
import sourabh.pal.cricket.common.presentation.model.mappers.UiPlayerMapper
import sourabh.pal.cricket.common.utils.DispatchersProvider
import sourabh.pal.cricket.playersnearyou.domain.usescases.GetPlayers
import sourabh.pal.cricket.playersnearyou.domain.usescases.RequestNextPageOfPlayers

@ExperimentalCoroutinesApi
class PlayersNearYouFragmentViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val rxImmediateSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var viewModel: PlayersNearYouFragmentViewModel
    private lateinit var repository: FakeRepository
    private lateinit var getPlayers: GetPlayers
    private lateinit var requestNextPageOfPlayers: RequestNextPageOfPlayers
    private val uiPlayerMapper: UiPlayerMapper = UiPlayerMapper()


    @Before
    fun setup() {
        val dispatcherProvider = object : DispatchersProvider {
            override fun io() = Dispatchers.Main
        }
        repository = FakeRepository()
        getPlayers = GetPlayers(repository)
        requestNextPageOfPlayers = RequestNextPageOfPlayers(repository)
        viewModel = PlayersNearYouFragmentViewModel(
            getPlayers,
            requestNextPageOfPlayers,
            uiPlayerMapper,
            dispatcherProvider,
            CompositeDisposable()
        )
    }

/*    @Test
    fun `PlayersNearYouFragmentViewModel fetch nearby players with success`() = testCoroutineRule.runBlockingTest {

        //Given
        val expectedPlayers = repository.localPlayers.map { uiPlayerMapper.mapToView(it) }

        viewModel.state.observeForever{}

        val expectedState = PlayerNearYouViewState(
            loading = false,
            players = expectedPlayers,
            noMorePlayersNearby = false,
            failure = null
        )
        //When
        viewModel.onEvent(PlayersNearYouEvent.RequireInitialPlayersList)

        //Then
        val viewState = viewModel.state.value!!

        assertThat(viewState).isEqualTo(expectedState)
    }*/

    @Test(expected = NetworkException::class)
    fun `PlayersNearYouFragmentViewModel fetch nearby players with failure`() = testCoroutineRule.runBlockingTest {

        //Given
        val dispatcherProvider = object : DispatchersProvider {
            override fun io() = Dispatchers.Main
        }
        repository.failApi = true
        viewModel = PlayersNearYouFragmentViewModel(
            getPlayers,
            requestNextPageOfPlayers,
            uiPlayerMapper,
            dispatcherProvider,
            CompositeDisposable()
        )
        viewModel.state.observeForever{}
        val expectedState = PlayerNearYouViewState(
            loading = false,
            players = emptyList(),
            noMorePlayersNearby = false,
            failure = Event(NetworkException("Cannot fetch players due to network exception"))
        )
        //When
        viewModel.onEvent(PlayersNearYouEvent.RequireInitialPlayersList)

        //Then
        val viewState = viewModel.state.value!!

        assertThat(viewState).isEqualTo(expectedState)
    }


    @Test
    fun `PlayersNearYouFragmentViewModel fetch more nearby players with success`() = testCoroutineRule.runBlockingTest {

        //Given
        val expectedPlayers = repository.localPlayers.map { uiPlayerMapper.mapToView(it) }

        viewModel.state.observeForever{}

        val expectedState = PlayerNearYouViewState(
            loading = false,
            players = expectedPlayers,
            noMorePlayersNearby = false,
            failure = null
        )
        //When
        viewModel.onEvent(PlayersNearYouEvent.RequireInitialPlayersList)

        //Then
        val viewState = viewModel.state.value!!

        assertThat(viewState).isEqualTo(expectedState)
    }

}