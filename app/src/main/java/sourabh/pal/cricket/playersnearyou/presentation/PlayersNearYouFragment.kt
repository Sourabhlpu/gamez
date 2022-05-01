package sourabh.pal.cricket.playersnearyou.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import sourabh.pal.cricket.common.presentation.PlayersAdapter
import sourabh.pal.cricket.databinding.FragmentPlayersNearYouBinding

@AndroidEntryPoint
class PlayersNearYouFragment: Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentPlayersNearYouBinding? = null

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
    }

    private fun setupUI() {
        val adapter = createAdapter()
        setupRecyclerView(adapter)
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
            override fun loadMoreItems() {
                TODO("Not yet implemented")
            }

            override fun isLastPage(): Boolean {
                TODO("Not yet implemented")
            }

            override fun isLoading(): Boolean {
                TODO("Not yet implemented")
            }
        }
    }

    private fun createAdapter(): PlayersAdapter {
        return PlayersAdapter()
    }
}