package ru.kudesnik.therickandmorty.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import ru.kudesnik.therickandmorty.databinding.MainFragmentBinding
import ru.kudesnik.therickandmorty.databinding.MainItemBinding
import ru.kudesnik.therickandmorty.model.entities.Character

class MainAdapter(private val itemClickListener: MainFragment.OnItemViewClickListener) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private var characterData: List<Character> = listOf()
    private lateinit var binding: MainItemBinding

    fun setCharacters(data: List<Character>) {
        characterData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        binding = MainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(characterData[position])
    }

    override fun getItemCount() = characterData.size


    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(character: Character) = with(binding) {
            characterName.text = character.name
            characterId.text = character.id.toString()
            root.setOnClickListener{itemClickListener.onItemViewClick(character)}
        }
    }
}