package ru.kudesnik.therickandmorty.model.entities.rest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.kudesnik.therickandmorty.model.entities.Location
import ru.kudesnik.therickandmorty.model.entities.rest.rest_entities.CharacterDTO
import ru.kudesnik.therickandmorty.model.entities.rest.rest_entities.CharacterListDTO
import ru.kudesnik.therickandmorty.model.entities.rest.rest_entities.EpisodeDTO

interface ModelAPI {
    @GET("character")
    fun getListCharacters(): Call<CharacterListDTO>

    @GET("character")
    fun getListCharactersWithPage(
        @Query("page") page: String?
    ): Call<CharacterListDTO>

    @GET("character/{listEpisode}")
    fun getListCharactersWithEpisode(
        @Path("listEpisode") page: String
    ): Call<List<CharacterDTO>>

    @GET("character/{id}")
    fun getCharacter(@Path("id") characterId: Int): Call<CharacterDTO>

    @GET("location")
    fun getLocation(): Call<List<Location>>

    @GET("episode/{stringEpisode}")
    fun getEpisode(
        @Path("stringEpisode") list: String
    ): Call<List<EpisodeDTO>>
}

