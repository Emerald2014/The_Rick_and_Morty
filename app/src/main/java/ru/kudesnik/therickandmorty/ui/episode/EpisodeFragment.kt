package ru.kudesnik.therickandmorty.ui.episode

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kudesnik.therickandmorty.R
import ru.kudesnik.therickandmorty.databinding.EpisodeFragmentBinding
import ru.kudesnik.therickandmorty.model.AppState
import ru.kudesnik.therickandmorty.ui.character.CharacterFragment

class EpisodeFragment : Fragment() {
    private val viewModel: EpisodeViewModel by viewModel()
    private var _binding: EpisodeFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EpisodeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(BUNDLE_EPISODE)?.let {
            viewModel.loadEpisodes(it)
            with(binding) {
                viewModel.episodeLiveData.observe(viewLifecycleOwner) {appState ->
                    when(appState) {
                        is AppState.SuccessEpisode -> {
                            for (i in appState.modelData.indices) {
                                containerForContacts.addView(TextView(requireContext()).apply { text = appState.modelData[i].name.toString() })
//                            episodeId.text = appState.modelData[i].id.toString()
                        }}
                    }
                }
            }
        }
    }

    companion object {
        const val BUNDLE_EPISODE = "episode"
        fun newInstance(bundle: Bundle): EpisodeFragment {
            val fragment = EpisodeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}