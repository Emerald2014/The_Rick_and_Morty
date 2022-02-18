package ru.kudesnik.therickandmorty.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
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

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.bottom_navigation, menu)
//        val item_dog = menu!!.findItem(R.id.btn_menu_prev_page)
//        // делаем его невидимым
//        // делаем его невидимым
//        item_dog.isVisible = false
//        super.onCreateOptionsMenu(menu, inflater)
//    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            AppState.Loading -> {
                progressBar.visibility = View.VISIBLE
            }
            is AppState.SuccessCharacter -> {
                progressBar.visibility = View.GONE

                mainFragmentRecyclerView.visibility = View.VISIBLE
//                btnCharacterNextPage.setOnClickListener(object : View.OnClickListener {
//                    override fun onClick(p0: View?) {
//                        viewModel.getCharacterListWithPage(appState.modelData[0].next)
//                    }
//
//                })
                val nav: BottomNavigationView = bottomNavigation

//                onCreateOptionsMenu(R.menu.bottom_navigation)
//                nav.findViewById<>(R.id.btn_menu_episode_character_list).setVisible(false)
                nav.setOnItemSelectedListener(object :
                    NavigationBarView.OnItemSelectedListener {
                    override fun onNavigationItemSelected(item: MenuItem): Boolean {
                        return when (item.itemId) {
                            R.id.btn_menu_prev_page -> {
                                if (appState.modelData[0].prev != null) {
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
                                    appState.modelData[0].next?.let { Log.d("myTag2", it) }

                                    viewModel.getCharacterListWithPage(appState.modelData[0].next)
                                } else {
                                    Toast.makeText(
                                        requireContext(), "Некуда переходить, ${appState.modelData[0].next}",
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
//                useBottomMenu(appState, nav)
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

//    private fun useBottomMenu(appState: AppState, nav: BottomNavigationView) {
//        nav.setOnItemSelectedListener(object :
//            NavigationBarView.OnItemSelectedListener {
//            override fun onNavigationItemSelected(item: MenuItem): Boolean {
//                return when (item.itemId) {
//                    R.id.btn_menu_prev_page -> {
//                        Toast.makeText(
//                            requireContext(),
//                            "Жму назад",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        true
//                    }
//                    R.id.btn_menu_episode_character_list -> {
//                        Toast.makeText(
//                            requireContext(),
//                            "Жму эпизод",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        val manager = activity?.supportFragmentManager
//                        manager?.let { manager ->
//                            val bundle = Bundle().apply {
//                                putString(
//                                    EpisodeFragment.BUNDLE_EPISODE,
//                                    appState.modelData[0].episode
//                                )
//                            }
//
//                            manager.beginTransaction()
//                                .add(
//                                    R.id.container,
//                                    EpisodeFragment.newInstance(bundle)
//                                )
//                                .addToBackStack("")
//                                .commitAllowingStateLoss()
//                        }
//
//                        true
//                    }
//                    R.id.btn_menu_next_page -> {
//                        Toast.makeText(
//                            requireContext(),
//                            "Жму вперед",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        viewModel.getCharacterListWithPage(appState.modelData[0].next)


//                                            viewModel.loadCharacter(it.id + 1)


//                                            val manager = activity?.supportFragmentManager
//                                            manager?.let { manager ->
//                                                val bundle = Bundle().apply {
//
//                                                    val nextId = appState.modelData[0].id + 1
//                                                    putInt(
//                                                        BUNDLE_CHARACTER,
//                                                        nextId
//                                                    )
//                                                }
//
//                                                manager.beginTransaction()
//                                                    .replace(
//                                                        R.id.container,
//                                                        newInstance(bundle)
//                                                    )
//                                                    .addToBackStack("")
//                                                    .commitAllowingStateLoss()
//                                            }
//

//                        true
//                    }
//                    else -> {
//                        false
//                    }
//                }
//            }
//        }
//
//        )
//    }

    interface OnItemViewClickListener {
        fun onItemViewClick(character: Character)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}