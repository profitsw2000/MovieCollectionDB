package ru.profitsw2000.moviecollectiondb.model.repository

import ru.profitsw2000.moviecollectiondb.model.Factory.CategoriesFactory.getCategories
import ru.profitsw2000.moviecollectiondb.model.Factory.MoviesFactory.getMovies
import ru.profitsw2000.moviecollectiondb.model.representation.Category
import ru.profitsw2000.moviecollectiondb.model.representation.Movie

/**
 *
 */
class RepositoryImpl : Repository {
    override fun getMovieFromServer() = Movie()
    override fun getMoviesFromLocalStorage() = getMovies(20)
    override fun getCategoriesFromLocalStorage() = getCategories(10)
}