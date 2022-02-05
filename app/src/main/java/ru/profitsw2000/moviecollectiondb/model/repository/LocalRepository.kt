package ru.profitsw2000.moviecollectiondb.model.repository

import ru.profitsw2000.moviecollectiondb.model.representation.Movie
import ru.profitsw2000.moviecollectiondb.room.NoteEntity

interface LocalRepository {
    suspend fun getAllNotes(): List<NoteEntity>
    suspend fun saveNote(movie: Movie, note: String): Long?
}