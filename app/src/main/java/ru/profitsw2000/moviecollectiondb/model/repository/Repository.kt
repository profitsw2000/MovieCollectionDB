package ru.profitsw2000.moviecollectiondb.model.repository

import ru.profitsw2000.moviecollectiondb.model.representation.Category
import ru.profitsw2000.moviecollectiondb.model.representation.Movie

interface Repository {
    fun getMovieFromServer(): Movie
    fun getMoviesFromLocalStorage(): List<Movie>
    fun getCategoriesFromLocalStorage(): List<Category>
}