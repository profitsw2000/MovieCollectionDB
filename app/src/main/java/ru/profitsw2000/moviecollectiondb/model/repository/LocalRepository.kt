package ru.profitsw2000.moviecollectiondb.model.repository

import ru.profitsw2000.moviecollectiondb.model.representation.Movie
import ru.profitsw2000.moviecollectiondb.room.NoteEntity

interface LocalRepository {
    fun getAllNotes(): List<NoteEntity>
    fun saveNote(movie: Movie)
}