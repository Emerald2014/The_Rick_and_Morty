package ru.kudesnik.therickandmorty.model.repository

import ru.kudesnik.therickandmorty.model.entities.Character
import ru.kudesnik.therickandmorty.model.entities.Episode

interface Repository {
    fun getAllCharacters(): List<Character>
    fun getCharacter(id: Int): Character
    fun getEpisodesWithCharacter(episodes: String): List<Episode>
}