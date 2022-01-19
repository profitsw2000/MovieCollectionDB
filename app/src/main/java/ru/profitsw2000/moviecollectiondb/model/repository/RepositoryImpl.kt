package ru.profitsw2000.moviecollectiondb.model.repository

import ru.profitsw2000.moviecollectiondb.model.representation.Movie
import ru.profitsw2000.moviecollectiondb.model.representation.getMovies

/**
 *
 */
class RepositoryImpl : Repository {
    override fun getMovieFromServer() = Movie()
    override fun getMoviesFromLocalStorage() = getMovies()
}