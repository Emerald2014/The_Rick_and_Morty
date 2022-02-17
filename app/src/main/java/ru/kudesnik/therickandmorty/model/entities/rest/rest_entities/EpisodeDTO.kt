package ru.kudesnik.therickandmorty.model.entities.rest.rest_entities

data class EpisodeDTO(
    val id: Int,
    val name: String,
    val air_date: String,
    val characters: List<String>
)
