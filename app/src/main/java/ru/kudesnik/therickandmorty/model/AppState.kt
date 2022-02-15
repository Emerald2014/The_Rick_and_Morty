package ru.kudesnik.therickandmorty.model

import ru.kudesnik.therickandmorty.model.entities.Character

sealed class AppState {
    data class Success(val modelData: List<Character>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}