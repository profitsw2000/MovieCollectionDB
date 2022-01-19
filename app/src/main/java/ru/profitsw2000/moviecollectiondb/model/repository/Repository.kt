package ru.profitsw2000.moviecollectiondb.model.repository

import ru.profitsw2000.moviecollectiondb.model.representation.Movie

interface Repository {
    fun getMovieFromServer(): Movie
    fun getMovieFromLocalStorage(): Movie
}