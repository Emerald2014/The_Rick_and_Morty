package ru.kudesnik.therickandmorty.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kudesnik.therickandmorty.R
import ru.kudesnik.therickandmorty.databinding.MainFragmentBinding
import ru.kudesnik.therickandmorty.model.AppState
import ru.kudesnik.therickandmorty.model.entities.Character
import ru.kudesnik.therickandmorty.ui.character.CharacterFragment

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModel()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private var adapter: MainAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            mainFragmentRecyclerView.adapter = adapter
            viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
            viewModel.getCharacterListFromServer()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            AppState.Loading -> {
                progressBar.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                progressBar.visibility = View.GONE
                adapter = MainAdapter(object : OnItemViewClickListener {
                    override fun onItemViewClick(character: Character) {
                        val manager = activity?.supportFragmentManager
                        manager?.let { manager ->
                            val bundle = Bundle().apply {
                                putParcelable(CharacterFragment.BUNDLE_EXTRA, character)
                            }
                            manager.beginTransaction()
                                .add(R.id.container, CharacterFragment.newInstance(bundle))
                                .addToBackStack("")
                                .commitAllowingStateLoss()
                        }
                    }
                }).apply { setCharacters(appState.modelData) }
                mainFragmentRecyclerView.adapter = adapter
            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(character: Character)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}