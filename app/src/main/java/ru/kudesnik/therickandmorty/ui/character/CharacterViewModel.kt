package ru.kudesnik.therickandmorty.ui.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kudesnik.therickandmorty.model.AppState
import ru.kudesnik.therickandmorty.model.entities.Character
import ru.kudesnik.therickandmorty.model.repository.Repository

class CharacterViewModel(private val repository: Repository) : ViewModel() {
    private val localeLiveData: MutableLiveData<AppState> = MutableLiveData()
    val characterLiveData: LiveData<AppState>
        get() {
            return localeLiveData
        }

    fun loadCharacter(id: Int) = with(viewModelScope) {
        localeLiveData.value = AppState.Loading

        launch(Dispatchers.IO) {
            val data = repository.getCharacter(id)
            withContext(Dispatchers.Main) {
                localeLiveData.value = AppState.SuccessCharacter(listOf(data))
            }
        }
    }

    fun loadCharacterData(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            localeLiveData.postValue(AppState.SuccessCharacter(listOf(repository.getCharacter(id))))
        }
    }
}