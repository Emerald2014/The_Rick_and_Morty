package ru.kudesnik.therickandmorty.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kudesnik.therickandmorty.R
import ru.kudesnik.therickandmorty.databinding.CharacterFragmentBinding
import ru.kudesnik.therickandmorty.model.AppState
import ru.kudesnik.therickandmorty.model.entities.Character
import ru.kudesnik.therickandmorty.ui.episode.EpisodeFragment
import ru.kudesnik.therickandmorty.ui.episode.EpisodeFragment.Companion.BUNDLE_EPISODE


class CharacterFragment : Fragment() {
    private val viewModel: CharacterViewModel by viewModel()
    private var _binding: CharacterFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = CharacterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Character>(BUNDLE_EXTRA)?.let {
            with(binding) {
                characterName.text = it.name
                btnGetEpisodes.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(p0: View?) {
//                        Toast.makeText(requireContext(), "Toast", Toast.LENGTH_SHORT).show()
                    }})


                viewModel.characterLiveData.observe(viewLifecycleOwner) { appState ->



                    when (appState) {
                        is AppState.SuccessCharacter -> {


                            btnGetEpisodes.setOnClickListener(object : View.OnClickListener {
                                override fun onClick(p0: View?) {
                                    Toast.makeText(requireContext(), "Toast", Toast.LENGTH_SHORT).show()
                                    val manager = activity?.supportFragmentManager
                                    manager?.let { manager ->
                                        val bundle = Bundle().apply {
                                            putString(
                                                BUNDLE_EPISODE,
                                                appState.modelData[0].episode
                                            )
                                        }

                                        manager.beginTransaction()
                                            .add(
                                                R.id.container,
                                                EpisodeFragment.newInstance(bundle)
                                            )
                                            .addToBackStack("")
                                            .commitAllowingStateLoss()
                                    }

                                }
                            })
                        }
                    }
                }
            }
            viewModel.loadCharacter(it.id)
        }
    }

    companion object {
        const val BUNDLE_EXTRA = "character"
        fun newInstance(bundle: Bundle): CharacterFragment {
            val fragment = CharacterFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}