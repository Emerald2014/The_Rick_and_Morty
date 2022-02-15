package ru.kudesnik.therickandmorty

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kudesnik.therickandmorty.model.repository.Repository
import ru.kudesnik.therickandmorty.model.repository.RepositoryImpl
import ru.kudesnik.therickandmorty.ui.character.CharacterViewModel
import ru.kudesnik.therickandmorty.ui.episode.EpisodeViewModel
import ru.kudesnik.therickandmorty.ui.main.MainViewModel

val appModule = module {
    single<Repository> { RepositoryImpl() }
    viewModel { MainViewModel(get()) }
    viewModel { CharacterViewModel(get()) }
    viewModel { EpisodeViewModel(get()) }
}