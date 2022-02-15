package ru.kudesnik.therickandmorty.model.repository

import android.util.Log
import ru.kudesnik.therickandmorty.model.entities.Character
import ru.kudesnik.therickandmorty.model.entities.Episode
import ru.kudesnik.therickandmorty.model.entities.rest.ModelRepo
import java.lang.StringBuilder

class RepositoryImpl : Repository {
    override fun getAllCharacters(): List<Character> {
        val dto = ModelRepo.api.getListCharacters().execute().body()
        val modelList = mutableListOf<Character>()

        if (dto != null) {
            for (index in dto.results.indices) {
                modelList.add(
                    Character(
                        id = dto.results[index].id,
                        name = dto.results[index].name,
                        status = dto.results[index].status,
                        species = dto.results[index].species,
                        type = dto.results[index].type,
                        gender = dto.results[index].gender,
                        locationName = dto.results[index].location.name,
                        image = dto.results[index].image,
                        episode = episodeListToString(dto.results[index].episode),
                        firstEpisode = firstEpisodeToString(dto.results[index].episode)

                    )
                )
            }
        }
        return modelList
    }

    override fun getCharacter(id: Int): Character {
        val dto = ModelRepo.api.getCharacter(id).execute().body()
        return Character(
            id = dto?.id ?: 0,
            name = dto?.name ?: "",
            status = dto?.status ?: "",
            species = dto?.species ?: "",
            type = dto?.type ?: "",
            gender = dto?.gender ?: "",
            locationName = dto?.location?.name ?: "",
            image = dto?.image ?: "",
            episode = episodeListToString(dto?.episode ?: listOf()),
            firstEpisode = firstEpisodeToString(dto?.episode ?: listOf())
        )
    }

    private fun episodeListToString(episodes: List<String>): String {
        val listOfEpisodeNumbers = StringBuilder()

        for (episodeUrl in episodes) {
            listOfEpisodeNumbers.append(episodeUrl.substring(episodeUrl.lastIndexOf('/') + 1)).append(",")
        }
        return listOfEpisodeNumbers.toString()
    }

    private fun firstEpisodeToString(episodes: List<String>): String {
        val listOfEpisodeNumbers = StringBuilder()
        listOfEpisodeNumbers.append(episodes[0].substring(episodes[0].lastIndexOf('/') + 1))
return listOfEpisodeNumbers.toString()

    }

    override fun getEpisodesWithCharacter(episodes: String): List<Episode> {
//        episodeStringToList(episodes)
        val dto = ModelRepo.api.getEpisode(episodes).execute().body()
        Log.d("mytag", episodes)
        val episode = mutableListOf<Episode>()
        if (dto != null) {
            for (index in dto.indices) {
                episode.add(
                    Episode(
                        id = dto[index].id,
                        name = dto[index].name,
                        characters = dto[index].characters,
                    )
                )

            }
        }
        return episode
    }
}