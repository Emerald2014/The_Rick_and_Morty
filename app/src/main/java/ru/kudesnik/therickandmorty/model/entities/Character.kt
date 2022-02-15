package ru.kudesnik.therickandmorty.model.entities

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val locationName: String,
    val image: String,
    val episode: List<String>
)
