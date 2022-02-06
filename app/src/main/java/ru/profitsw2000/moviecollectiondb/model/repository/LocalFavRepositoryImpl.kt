package ru.profitsw2000.moviecollectiondb.model.repository

import ru.profitsw2000.moviecollectiondb.model.representation.Movie
import ru.profitsw2000.moviecollectiondb.room.FavoriteDAO
import ru.profitsw2000.moviecollectiondb.room.FavoriteEntity
import ru.profitsw2000.moviecollectiondb.room.NoteDAO
import ru.profitsw2000.moviecollectiondb.utils.convertMovieToTitle

class LocalFavRepositoryImpl(private val localDataSource: FavoriteDAO) : LocalFavRepository {
    override suspend fun getAllFavoriteMovies(): List<FavoriteEntity> {
        return localDataSource.all()
    }

    override suspend fun getFavoriteMovie(title: String): List<FavoriteEntity> {
        return localDataSource.getDataByWord(title)
    }

    override suspend fun saveMovieToFavorite(movie: Movie) {
        localDataSource.insert(convertMovieToTitle(movie))
    }
}