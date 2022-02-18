package ru.kudesnik.therickandmorty.ui.episode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kudesnik.therickandmorty.R
import ru.kudesnik.therickandmorty.databinding.EpisodeFragmentBinding
import ru.kudesnik.therickandmorty.model.AppState
import ru.kudesnik.therickandmorty.ui.main.MainFragment

class EpisodeFragment : Fragment() {
    private val viewModel: EpisodeViewModel by viewModel()
    private var _binding: EpisodeFragmentBinding? = null
    private val binding get() = _binding!!
    private var adapter: EpisodeAdapter? = null

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
            with(binding) {
                episodeFragmentRecyclerView.adapter = adapter
                viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
                viewModel.loadEpisodes(it)

            }
        }
    }


/*                viewModel.loadEpisodes(it)

                viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })


                viewModel.episodeLiveData.observe(viewLifecycleOwner) { appState ->
                    when (appState) {
                        is AppState.SuccessEpisode -> {
                            for (i in appState.modelData.indices) {
//                                    containerForContacts.addView(TextView(requireContext()).apply {
//                                        text = appState.modelData[i].name.toString()
//                                    })
//                            episodeId.text = appState.modelData[i].id.toString()
                            }
                        }
                    }
                }
            }
        }
    }*/


    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.SuccessEpisode -> {
                progressBar.visibility = View.GONE
                adapter = EpisodeAdapter(object : OnItemViewClickListener {
                    override fun onItemViewClick(listEpisode:String) {
                        val manager = activity?.supportFragmentManager
                        manager?.let { manager ->
                            val bundle = Bundle().apply {
                                putString(MainFragment.BUNDLE_MAIN, appState.modelData[0].characters)
                            }
                            manager.beginTransaction()
                                .add(R.id.container, MainFragment.newInstance(bundle))
                                .addToBackStack("")
                                .commitAllowingStateLoss()
                        }
                    }
                }).apply { setEpisode(appState.modelData) }

                episodeFragmentRecyclerView.adapter = adapter

            }
            is AppState.Loading -> {
                progressBar.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                progressBar.visibility = View.GONE
                episodeFragmentRootView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        arguments?.getString(BUNDLE_EPISODE)?.let {
                            with(binding) {
                                episodeFragmentRecyclerView.adapter = adapter

                                viewModel.loadEpisodes(it)


                            }
                        }
                    })
            }
        }
    }


    private fun View.showSnackBar(
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, text, length).setAction(actionText, action).show()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(listEpisode:String)
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