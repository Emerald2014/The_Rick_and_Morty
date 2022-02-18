package ru.kudesnik.therickandmorty.ui.character

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.load
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar
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


        setHasOptionsMenu(true)
        _binding = CharacterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Character>(BUNDLE_CHARACTER)?.let { it ->
            with(binding) {
                characterName.text = it.name
                characterLocation.text = it.locationName
                characterPhoto.load(it.image) {
                    crossfade(true)
                    error(R.drawable.no_image)
                    placeholder(R.drawable.no_poster)
                }
                characterEpisode.text = it.firstEpisode
                characterSpecies.text = it.species
                characterStatus.text = it.status

                val nav: BottomNavigationView = bottomNavigation

                when (it.status) {
                    "Alive" -> {
                        characterStatusFlag.load(R.drawable.circle_20_green)
                    }
                    "Dead" -> {
                        characterStatusFlag.load(R.drawable.circle_20_red)
                    }
                    else -> {
                        characterStatusFlag.load(R.drawable.circle_20_unknown)
                    }
                }
//                when (it.id) {
//                    1 -> {
//                        btnPrevPage.visibility = View.GONE
//                    }
//
//                }

                viewModel.characterLiveData.observe(viewLifecycleOwner) { appState ->

                    when (appState) {
                        is AppState.Loading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                        is AppState.SuccessCharacter -> {
                            progressBar.visibility = View.GONE

                            nav.setOnItemSelectedListener(object :
                                NavigationBarView.OnItemSelectedListener {
                                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                                    return when (item.itemId) {
                                        R.id.btn_menu_prev_page -> {
                                            Toast.makeText(
                                                requireContext(),
                                                "Жму назад",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            true
                                        }
                                        R.id.btn_menu_episode_character_list -> {
                                            Toast.makeText(
                                                requireContext(),
                                                "Жму эпизод",
                                                Toast.LENGTH_SHORT
                                            ).show()
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

                                            true
                                        }
                                        R.id.btn_menu_next_page -> {
                                            Toast.makeText(
                                                requireContext(),
                                                "Жму вперед",
                                                Toast.LENGTH_SHORT
                                            ).show()

//                                            viewModel.loadCharacter(it.id + 1)


                                            val manager = activity?.supportFragmentManager
                                            manager?.let { manager ->
                                                val bundle = Bundle().apply {
                                                    viewModel.loadCharacterData(it.id+1)
                                                    putParcelable(
                                                        BUNDLE_CHARACTER,
                                                        appState.modelData[0]
                                                    )
                                                }

                                                manager.beginTransaction()
                                                    .replace(
                                                        R.id.container,
                                                        newInstance(bundle)
                                                    )
                                                    .addToBackStack("")
                                                    .commitAllowingStateLoss()
                                            }
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

                                            true
                                        }
                                        else -> {
                                            false
                                        }
                                    }
                                }
                            }

                            )


/*
                                                   btnGetEpisodes.setOnClickListener(object : View.OnClickListener {
                                                       override fun onClick(p0: View?) {
                       //                                    Toast.makeText(requireContext(), "Toast", Toast.LENGTH_SHORT).show()
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
                                                   btnNextPage.setOnClickListener(object : View.OnClickListener {
                                                       override fun onClick(p0: View?) {
                                                           val manager = activity?.supportFragmentManager
                                                           manager?.let { manager ->
                                                               val bundle = Bundle().apply {

                                                                   val nextId = appState.modelData[0].id + 1
                                                                   putInt(
                                                                       BUNDLE_CHARACTER,
                                                                       nextId
                                                                   )
                                                               }

                                                               manager.beginTransaction()
                                                                   .replace(
                                                                       R.id.container,
                                                                       newInstance(bundle)
                                                                   )
                                                                   .addToBackStack("")
                                                                   .commitAllowingStateLoss()
                                                           }

                                                       }
                                                   }


                                                   )
                                               }*/
                        }
                        is AppState.Error -> {
                            progressBar.visibility = View.GONE
                            characterFragmentRootView.showSnackBar(
                                getString(R.string.error),
                                getString(R.string.reload),
                                {
                                    viewModel.loadCharacter(it.id)
                                })
                        }
                    }
                }
            }
            viewModel.loadCharacter(it.id)
        }
    }

    /*    private fun openFragment(appState: AppState) {
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
        }*/
    private fun View.showSnackBar(
        text: String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, text, length).setAction(actionText, action).show()
    }

    fun NavigationBarView.OnItemSelectedListener() {

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.bottom_character_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    /*   override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

           inflater.inflate(R.menu.bottom_navigation, menu)
           arguments?.getParcelable<Character>(BUNDLE_CHARACTER)?.let {
               when (it.id) {
                   1 -> {
                       Toast.makeText(requireContext(), "Жмакаю назад", Toast.LENGTH_SHORT).show()

                       val itemPrev = menu.findItem(R.id.btn_menu_prev_page)
                       itemPrev.isVisible = false
                   }
               }
           }
       }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_menu_prev_page -> {
                Toast.makeText(requireContext(), "Жмакаю назад", Toast.LENGTH_SHORT).show()
                arguments?.getParcelable<Character>(BUNDLE_CHARACTER)?.let {
                    viewModel.loadCharacter(it.id - 1)
                }
                true
            }
            else -> {
                false
            }
        }
    }

    //    private fun openFragment(fragment: Fragment) {
//        supportFragmentManager.apply {
//            beginTransaction()
//                .add(R.id.container, fragment)
//                .addToBackStack("")
//                .commitAllowingStateLoss()
//        }
//    }
    companion object {
        const val BUNDLE_CHARACTER = "character"
        fun newInstance(bundle: Bundle): CharacterFragment {
            val fragment = CharacterFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}