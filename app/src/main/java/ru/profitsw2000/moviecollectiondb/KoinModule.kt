package ru.profitsw2000.moviecollectiondb

import org.koin.dsl.module
import ru.profitsw2000.moviecollectiondb.model.repository.Repository
import ru.profitsw2000.moviecollectiondb.model.repository.RepositoryImpl

val appModule = module {
    single<Repository> {RepositoryImpl()}
}