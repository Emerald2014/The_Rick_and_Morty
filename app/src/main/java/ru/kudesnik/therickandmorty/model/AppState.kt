package ru.kudesnik.therickandmorty.model

import ru.kudesnik.therickandmorty.model.entities.Character
import ru.kudesnik.therickandmorty.model.entities.Episode

sealed class AppState {
    data class SuccessCharacter(val modelData: List<Character>) : AppState()
    data class SuccessEpisode(val modelData: List<Episode>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}