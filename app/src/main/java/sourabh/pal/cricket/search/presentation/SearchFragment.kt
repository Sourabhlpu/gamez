package sourabh.pal.cricket.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import sourabh.pal.cricket.common.domain.model.player.Player
import sourabh.pal.cricket.common.presentation.PlayersAdapter
import sourabh.pal.cricket.databinding.FragmentSearchBinding

@AndroidEntryPoint
class SearchFragment: Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentSearchBinding? = null

    private val viewModel: SearchFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        val adapter = createAdapter()
        setupRecyclerView(adapter)
        //observeViewStateUpdates(adapter)
    }

    private fun setupRecyclerView(searchAdapter: PlayersAdapter) {
        binding.searchRecyclerView.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun createAdapter(): PlayersAdapter {
        return PlayersAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}