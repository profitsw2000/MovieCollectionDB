package ru.profitsw2000.moviecollectiondb

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.profitsw2000.moviecollectiondb.model.repository.*
import ru.profitsw2000.moviecollectiondb.retrofit.RemoteDataSource
import ru.profitsw2000.moviecollectiondb.room.NoteDAO
import ru.profitsw2000.moviecollectiondb.ui.description.DescriptionViewModel
import ru.profitsw2000.moviecollectiondb.ui.main.MainViewModel
import ru.profitsw2000.moviecollectiondb.ui.notes.NoteViewModel

val appModule = module {
    single<Repository> {RepositoryImpl()}
    single<DescriptionRepository> {DescriptionRepositoryImpl(RemoteDataSource())}
    single<LocalRepository> {LocalRepositoryImpl(App.getNoteDao())}

    //View models
    viewModel { MainViewModel(get()) }
    viewModel { DescriptionViewModel(get()) }
    viewModel { NoteViewModel(get()) }
}