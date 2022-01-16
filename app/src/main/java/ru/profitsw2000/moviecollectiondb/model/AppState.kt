package ru.profitsw2000.moviecollectiondb.model

import ru.profitsw2000.moviecollectiondb.model.representation.Movie

sealed class AppState {
    data class Success(val movie: Movie) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
