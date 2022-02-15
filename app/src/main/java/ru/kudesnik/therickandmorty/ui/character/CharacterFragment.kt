package ru.kudesnik.therickandmorty.ui.character

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kudesnik.therickandmorty.databinding.CharacterFragmentBinding
import ru.kudesnik.therickandmorty.model.entities.Character


class CharacterFragment : Fragment() {
private val viewModel :CharacterViewModel by viewModel()
private var _binding: CharacterFragmentBinding? = null
private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding=  CharacterFragmentBinding.inflate(inflater, container, false)
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

            }
        }
    }
    companion object {
        const val BUNDLE_EXTRA = "character"
        fun newInstance(bundle: Bundle) : CharacterFragment{
            val fragment = CharacterFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}