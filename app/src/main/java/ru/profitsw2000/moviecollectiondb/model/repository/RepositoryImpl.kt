package ru.profitsw2000.moviecollectiondb.model.repository

import ru.profitsw2000.moviecollectiondb.model.representation.Movie

/**
 *
 */
class RepositoryImpl : Repository {
    override fun getMovieFromServer() = Movie() ;

    override fun getMovieFromLocalStorage() = Movie()

}