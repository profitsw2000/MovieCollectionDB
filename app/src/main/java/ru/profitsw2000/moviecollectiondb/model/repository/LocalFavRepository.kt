package ru.profitsw2000.moviecollectiondb.model.repository

import ru.profitsw2000.moviecollectiondb.model.representation.Movie
import ru.profitsw2000.moviecollectiondb.room.FavoriteEntity
import ru.profitsw2000.moviecollectiondb.room.NoteEntity

interface LocalFavRepository {
    suspend fun getAllFavoriteMovies(): List<FavoriteEntity>
    suspend fun getFavoriteMovie(title: String): List<FavoriteEntity>
    suspend fun saveMovieToFavorite(movie: Movie)
}