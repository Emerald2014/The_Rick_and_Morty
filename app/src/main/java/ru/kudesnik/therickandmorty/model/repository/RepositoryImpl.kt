package ru.kudesnik.therickandmorty.model.repository

import ru.kudesnik.therickandmorty.model.entities.Character
import ru.kudesnik.therickandmorty.model.entities.Episode
import ru.kudesnik.therickandmorty.model.entities.rest.ModelRepo

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
                        episode = dto.results[index].episode
                    )
                )
            }
        }
        return modelList
    }

    override fun getCharacter(id: Int): Character {
        val dto = ModelRepo.api.getCharacter(id).execute().body()
  val   episodeList =    if (dto != null) {
            episodeStringToList(dto.episode)
        } else listOf()
        return Character(
            id = dto?.id ?: 0,
            name = dto?.name ?: "",
            status = dto?.status ?: "",
            species = dto?.species ?: "",
            type = dto?.type ?: "",
            gender = dto?.gender ?: "",
            locationName = dto?.location?.name ?: "",
            image = dto?.image ?: "",
            episode = episodeList
        )
    }

    private fun episodeStringToList(episodes: List<String>): List<String> {
        val listOfEpisodeNumbers = mutableListOf<String>()

        for (episodeUrl in episodes) {
            listOfEpisodeNumbers.add(episodeUrl.substring(episodeUrl.lastIndexOf('/')))
        }
        return listOfEpisodeNumbers.toList()
    }

    override fun getEpisodesWithCharacter(episodes: List<String>): List<Episode> {
        episodeStringToList(episodes)
        val dto = ModelRepo.api.getEpisode(episodes).execute().body()
        val episode = mutableListOf<Episode>()
        if (dto != null) {
            for (index in dto.results.indices) {
                episode.add(
                    Episode(
                        id = dto.results[index].id,
                        name = dto.results[index].name,
                        characters = dto.results[index].characters,
                    )
                )

            }
        }
        return episode
    }
}