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
                        episode = episodeListToString(dto.results[index].episode),
                        firstEpisode = firstEpisodeToString(dto.results[index].episode),
                        next = dto.info.next,
                        prev = dto.info.prev
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
            firstEpisode = firstEpisodeToString(dto?.episode ?: listOf()),
            next = "",
            prev = ""
        )
    }

    private fun episodeListToString(episodes: List<String>): String {
        val listOfEpisodeNumbers = StringBuilder()

        for (episodeUrl in episodes) {
            listOfEpisodeNumbers.append(episodeUrl.substring(episodeUrl.lastIndexOf('/') + 1))
                .append(",")
        }
        return listOfEpisodeNumbers.toString()
    }

    private fun firstEpisodeToString(episodes: List<String>): String {
        val listOfEpisodeNumbers = StringBuilder()
        listOfEpisodeNumbers.append(episodes[0].substring(episodes[0].lastIndexOf('/') + 1))
        return listOfEpisodeNumbers.toString()

    }

    override fun getEpisodesWithCharacter(episodes: String): List<Episode> {
        val dto = ModelRepo.api.getEpisode(episodes).execute().body()
        val episode = mutableListOf<Episode>()
        if (dto != null) {
            for (index in dto.indices) {
                episode.add(
                    Episode(
                        id = dto[index].id,
                        name = dto[index].name,
                        air_date = dto[index].air_date,
                        characters = episodeListToString(dto[index].characters),
                    )
                )
            }
        }
        return episode
    }

    override fun getAllCharactersWithPage(page: String?): List<Character> {
        val dto = ModelRepo.api.getListCharactersWithPage(pageToInt(page)).execute().body()
        val modelList = mutableListOf<Character>()
        val nextPage = dto?.info?.let { pageToInt(it.next) }
        val prevPage = dto?.info?.let { pageToInt(it.prev) }
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
                        firstEpisode = firstEpisodeToString(dto.results[index].episode),
                        next = nextPage,
                        prev = prevPage
                    )
                )
            }
        }
        return modelList
    }

    private fun pageToInt(page: String?): String {
        val pageNumber = StringBuilder()
        if (page != null) {
            pageNumber.append(page.substring(page.lastIndexOf('=') + 1))
        }
        return pageNumber.toString()
    }

    override fun getAllCharactersWithEpisode(listEpisode: String): List<Character> {
        val dto =
            ModelRepo.api.getListCharactersWithEpisode(pageToInt(listEpisode)).execute().body()
        val modelList = mutableListOf<Character>()
        if (dto != null) {
            for (index in dto.indices) {
                modelList.add(
                    Character(
                        id = dto[index].id,
                        name = dto[index].name,
                        status = dto[index].status,
                        species = dto[index].species,
                        type = dto[index].type,
                        gender = dto[index].gender,
                        locationName = dto[index].location.name,
                        image = dto[index].image,
                        episode = episodeListToString(dto[index].episode),
                        firstEpisode = firstEpisodeToString(dto[index].episode),
                        next = "",
                        prev = ""
                    )
                )
            }
        }
        return modelList
    }
}


