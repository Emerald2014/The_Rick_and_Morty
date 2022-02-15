package ru.kudesnik.therickandmorty.ui.episode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kudesnik.therickandmorty.model.AppState
import ru.kudesnik.therickandmorty.model.repository.Repository

class EpisodeViewModel(private val repository: Repository) : ViewModel() {
    private val localeLiveData: MutableLiveData<AppState> = MutableLiveData()
    val episodeLiveData: LiveData<AppState>
        get() {
            return localeLiveData
        }

    fun loadEpisodes(listEpisodes: List<String>) = with(viewModelScope) {
        localeLiveData.value = AppState.Loading
        launch(Dispatchers.IO) {
            val dataEpisode = repository.getEpisodesWithCharacter(listEpisodes)
            withContext(Dispatchers.Main) {
                localeLiveData.value = AppState.SuccessEpisode(dataEpisode)
            }
        }
    }
}