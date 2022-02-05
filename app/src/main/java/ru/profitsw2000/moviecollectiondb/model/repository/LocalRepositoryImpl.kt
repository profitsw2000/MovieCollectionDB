package ru.profitsw2000.moviecollectiondb.model.repository

import ru.profitsw2000.moviecollectiondb.model.representation.Movie
import ru.profitsw2000.moviecollectiondb.room.NoteDAO
import ru.profitsw2000.moviecollectiondb.room.NoteEntity
import ru.profitsw2000.moviecollectiondb.utils.convertMovieToNoteEntity

class LocalRepositoryImpl(private val localDataSource: NoteDAO) : LocalRepository {
    override suspend fun getAllNotes(): List<NoteEntity> {
        return localDataSource.all()
    }

    override suspend fun saveNote(movie: Movie, note: String): Long? {
        return localDataSource.insert(convertMovieToNoteEntity(movie, note))
    }

}