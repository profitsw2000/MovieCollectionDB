package ru.profitsw2000.moviecollectiondb.model.repository

import ru.profitsw2000.moviecollectiondb.model.representation.Movie
import ru.profitsw2000.moviecollectiondb.room.NoteDAO
import ru.profitsw2000.moviecollectiondb.room.NoteEntity

class LocalRepositoryImpl(private val localDataSource: NoteDAO) : LocalRepository {
    override fun getAllNotes(): List<NoteEntity> {
        TODO("Not yet implemented")
    }

    override fun saveNote(movie: Movie) {
        TODO("Not yet implemented")
    }

}