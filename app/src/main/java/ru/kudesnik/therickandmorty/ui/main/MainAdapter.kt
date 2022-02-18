package ru.kudesnik.therickandmorty.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.kudesnik.therickandmorty.R
import ru.kudesnik.therickandmorty.databinding.MainItemBinding
import ru.kudesnik.therickandmorty.model.entities.Character

class MainAdapter(private val itemClickListener: MainFragment.OnItemViewClickListener) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private var characterData: List<Character> = listOf()

    private lateinit var binding: MainItemBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setCharacters(data: List<Character>) {
        characterData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        binding = MainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(characterData[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount() = characterData.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(character: Character) = with(binding) {
            characterName.text = character.name
            characterLocation.text = character.locationName
            characterPhoto.load(character.image) {
                crossfade(true)
                error(R.drawable.no_image)
                placeholder(R.drawable.no_poster)
            }
            characterEpisode.text = character.firstEpisode
            characterSpecies.text = character.species
            characterStatus.text = character.status
            when (character.status) {
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
            root.setOnClickListener { itemClickListener.onItemViewClick(character) }
        }
    }
}