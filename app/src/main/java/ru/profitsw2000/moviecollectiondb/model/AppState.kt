package ru.profitsw2000.moviecollectiondb.model

import ru.profitsw2000.moviecollectiondb.model.representation.Category
import ru.profitsw2000.moviecollectiondb.model.representation.Movie
import ru.profitsw2000.moviecollectiondb.room.NoteEntity

sealed class AppState {
    data class Success(val categoriesData: List<Category>) : AppState()
    data class MovieSuccess(val movie: Movie) : AppState()
    data class NotesSuccess(val noteList: List<NoteEntity>) : AppState()
    data class Error(val message: String) : AppState()
    object Loading : AppState()
}
