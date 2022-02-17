package ru.kudesnik.therickandmorty.ui.episode

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kudesnik.therickandmorty.databinding.EpisodeItemBinding
import ru.kudesnik.therickandmorty.model.entities.Episode

class EpisodeAdapter() :
    RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {
    private var episodeData: List<Episode> = listOf()
    private lateinit var binding: EpisodeItemBinding

    fun setEpisode(data: List<Episode>) {
        episodeData = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodeAdapter.EpisodeViewHolder {
        binding = EpisodeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodeViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: EpisodeAdapter.EpisodeViewHolder, position: Int) {
        holder.setIsRecyclable(false);
        holder.bind(episodeData[position])
    }

    override fun getItemCount() = episodeData.size


    inner class EpisodeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(episode: Episode) = with(binding) {
            episodeId.text = episode.id.toString()
            episodeName.text = episode.name
            episodeAirDate.text = episode.air_date
        }
    }

}