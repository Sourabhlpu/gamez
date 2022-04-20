package sourabh.pal.cricket.playersnearyou.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
    }

    private fun createAdapter(): PlayersAdapter {
        return PlayersAdapter()
    }
}