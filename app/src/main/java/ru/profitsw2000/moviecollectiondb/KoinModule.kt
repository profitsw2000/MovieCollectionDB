package ru.profitsw2000.moviecollectiondb

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.profitsw2000.moviecollectiondb.model.repository.Repository
import ru.profitsw2000.moviecollectiondb.model.repository.RepositoryImpl
import ru.profitsw2000.moviecollectiondb.ui.description.DescriptionViewModel
import ru.profitsw2000.moviecollectiondb.ui.main.MainViewModel

val appModule = module {
    single<Repository> {RepositoryImpl()}

    //View models
    viewModel { MainViewModel(get()) }
    viewModel { DescriptionViewModel(get()) }
}