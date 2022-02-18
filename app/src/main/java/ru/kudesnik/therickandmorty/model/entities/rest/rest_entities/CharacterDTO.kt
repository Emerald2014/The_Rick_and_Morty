package ru.kudesnik.therickandmorty.model.entities.rest.rest_entities

data class CharacterDTO(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val location: LocationDTO,
    val image: String,
    val episode: List<String>,
    val next: String?,
    val prev: String?
)
