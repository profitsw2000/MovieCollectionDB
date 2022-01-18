package ru.profitsw2000.moviecollectiondb.model

import ru.profitsw2000.moviecollectiondb.model.representation.Movie

sealed class AppState {
    data class Success(val moviesData: List<Movie>) : AppState()
    data class Error(val message: String) : AppState()
    object Loading : AppState()
}
