package ru.profitsw2000.moviecollectiondb.model.repository

import ru.profitsw2000.moviecollectiondb.model.representation.Category
import ru.profitsw2000.moviecollectiondb.model.representation.Movie

interface Repository {
    fun getMovieFromServer(id: Int): Movie
    fun getMoviesFromServer(request: String): List<Movie>
    fun getCategoriesFromServer(request: String): List<Category>
    fun getMoviesFromLocalStorage(): List<Movie>
    fun getCategoriesFromLocalStorage(): List<Category>
}