package ru.kudesnik.therickandmorty.model.entities.rest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kudesnik.therickandmorty.model.entities.Character
import ru.kudesnik.therickandmorty.model.entities.Episode
import ru.kudesnik.therickandmorty.model.entities.Location
import ru.kudesnik.therickandmorty.model.entities.rest.rest_entities.CharacterDTO
import ru.kudesnik.therickandmorty.model.entities.rest.rest_entities.CharacterListDTO
import ru.kudesnik.therickandmorty.model.entities.rest.rest_entities.EpisodeDTO
import ru.kudesnik.therickandmorty.model.entities.rest.rest_entities.EpisodeListDTO

interface ModelAPI {
    @GET("character")
    fun getListCharacters(): Call<CharacterListDTO>

    @GET("character")

    fun getCharacter(@Query("") characterId: Int): Call<CharacterDTO>

    @GET("location")
    fun getLocation(): Call<List<Location>>

    @GET("episode")
    fun getEpisode(
       @Query("") list: List<String>): Call<EpisodeListDTO>
}

