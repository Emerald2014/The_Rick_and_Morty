package ru.kudesnik.therickandmorty.model.entities

data class Episode(
    val id: Int,
    val name: String,
    val characters: List<String>
)
