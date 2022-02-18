package ru.kudesnik.therickandmorty.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar
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
        arguments?.getString(BUNDLE_MAIN)?.let {

            with(binding) {
                mainFragmentRecyclerView.adapter = adapter
                viewModel.getCharacterListWithEpisode(it)
                viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }

                mainSwipe.setOnRefreshListener {
                    viewModel.getCharacterListWithEpisode(it)
                    mainSwipe.isRefreshing = false
                }
            }
        }
        with(binding) {
            mainFragmentRecyclerView.adapter = adapter
            viewModel.getCharacterListFromServer()
            viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }

            mainSwipe.setOnRefreshListener {
                viewModel.getCharacterListFromServer()
                mainSwipe.isRefreshing = false
            }
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
            is AppState.SuccessCharacter -> {
                progressBar.visibility = View.GONE

                mainFragmentRecyclerView.visibility = View.VISIBLE

                val nav: BottomNavigationView = bottomNavigation
                nav.setOnItemSelectedListener(object :
                    NavigationBarView.OnItemSelectedListener {
                    override fun onNavigationItemSelected(item: MenuItem): Boolean {
                        return when (item.itemId) {
                            R.id.btn_menu_prev_page -> {
                                if (appState.modelData[0].prev != null || appState.modelData[0].prev == "") {
                                    viewModel.getCharacterListWithPage(appState.modelData[0].prev)
                                } else {
                                    Toast.makeText(
                                        requireContext(), "Некуда переходить",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                true
                            }
                            R.id.btn_menu_next_page -> {
                                if (appState.modelData[0].next != null) {
                                    viewModel.getCharacterListWithPage(appState.modelData[0].next)
                                } else {
                                    Toast.makeText(
                                        requireContext(), "Некуда переходить",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                true
                            }
                            else -> {
                                false
                            }
                        }
                    }
                })
                adapter = MainAdapter(object : OnItemViewClickListener {
                    override fun onItemViewClick(character: Character) {
                        val manager = activity?.supportFragmentManager
                        manager?.let { manager ->
                            val bundle = Bundle().apply {
                                putParcelable(CharacterFragment.BUNDLE_CHARACTER, character)
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
            is AppState.Error -> {
                progressBar.visibility = View.GONE
                mainFragmentRootView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        viewModel.getCharacterListFromServer()
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
        fun onItemViewClick(character: Character)
    }

    companion object {
        fun newInstance() = MainFragment()
        const val BUNDLE_MAIN = "main"
        fun newInstance(bundle: Bundle): MainFragment {
            val fragment = MainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}