package ru.kudesnik.therickandmorty.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.kudesnik.therickandmorty.model.AppState
import ru.kudesnik.therickandmorty.model.repository.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {
private val liveData = MutableLiveData<AppState>()
    fun getLiveData(): LiveData<AppState> = liveData

    fun getCharacterListFromServer() {
        liveData.value = AppState.Loading
        viewModelScope.launch (Dispatchers.IO){
            delay(1000)
            liveData.postValue(AppState.Success(repository.getAllCharacters())) }
    }


}