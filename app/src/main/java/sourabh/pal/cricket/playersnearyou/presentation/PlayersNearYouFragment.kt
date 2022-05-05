package sourabh.pal.cricket.playersnearyou.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import sourabh.pal.cricket.R
import sourabh.pal.cricket.common.presentation.Event
import sourabh.pal.cricket.common.presentation.PlayersAdapter
import sourabh.pal.cricket.databinding.FragmentPlayersNearYouBinding

@AndroidEntryPoint
class PlayersNearYouFragment: Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentPlayersNearYouBinding? = null
    private val viewModel: PlayersNearYouFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayersNearYouBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        requestInitialAnimalsList()
    }

    private fun setupUI() {
        val adapter = createAdapter()
        setupRecyclerView(adapter)
        observeViewStateUpdates(adapter)
    }


    private fun createAdapter(): PlayersAdapter {
        return PlayersAdapter()
    }

    private fun setupRecyclerView(playersAdapter: PlayersAdapter) {
        binding.animalsRecyclerView.apply {
            adapter = playersAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addOnScrollListener(createInfiniteScrollListener(layoutManager as LinearLayoutManager))
        }

    }

    private fun createInfiniteScrollListener(layoutManager: LinearLayoutManager): RecyclerView.OnScrollListener {
        return object: InfiniteScrollListener(
            layoutManager,
            10
        ){
            override fun loadMoreItems() { requestMorePlayers() }
            override fun isLastPage(): Boolean = viewModel.isLastPage
            override fun isLoading(): Boolean = viewModel.isLoadingMorePlayers
        }
    }


    private fun observeViewStateUpdates(adapter: PlayersAdapter) {
        viewModel.state.observe(viewLifecycleOwner){
            updateScreen(it, adapter)
        }
    }

    private fun updateScreen(state: PlayerNearYouViewState, adapter: PlayersAdapter) {
        binding.progressBar.isVisible = state.loading
        adapter.submitList(state.players)
        handleNoMorePlayersNearby(state.noMorePlayersNearby)
        handleFailures(state.failure)
    }


    private fun handleNoMorePlayersNearby(noMorePlayersNearby: Boolean) {
        //TODO
    }

    private fun requestMorePlayers() {
        viewModel.onEvent(PlayersNearYouEvent.RequestMorePlayers)
    }

    private fun requestInitialAnimalsList() {
        viewModel.onEvent(PlayersNearYouEvent.RequireInitialPlayersList)
    }

    private fun handleFailures(failure: Event<Throwable>?) {
        val unhandledFailure = failure?.getContentIfNotHandled() ?: return

        val fallbackMessage = getString(R.string.an_error_occurred)

        val snackbarMessage = if (unhandledFailure.message.isNullOrEmpty()) {
            fallbackMessage
        }
        else {
            unhandledFailure.message!! }
        if (snackbarMessage.isNotEmpty()) {
            Snackbar.make(requireView(), snackbarMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}