package ru.profitsw2000.moviecollectiondb.model.Requests

import ru.profitsw2000.moviecollectiondb.BuildConfig


private const val API_KEY = BuildConfig.MOVIE_API_KEY

private const val REQUEST_BEGIN = "https://api.themoviedb.org/3/"
private const val REQUEST_GENRE_PART = "genre/movie/list"
private const val REQUEST_GENRE_ID = "&page=1&with_genres="
private const val REQUEST_INCLUDE_ADULT = "&include_adult="
private const val REQUEST_DISCOVERY_PART = "discover/movie"
private const val REQUEST_MOVIE_PART = "movie/"
private const val REQUEST_LANGUAGE_PART = "&language=ru"
private const val REQUEST_API_KEY_PART = "?api_key=$API_KEY"

object RequestGenerator {

    fun getGenresListRQ(): String {
        return REQUEST_BEGIN + REQUEST_GENRE_PART + REQUEST_API_KEY_PART + REQUEST_LANGUAGE_PART
    }

    fun getMovieListByGenreRQ(id: Int, includeAdult: Boolean): String {
        return REQUEST_BEGIN + REQUEST_DISCOVERY_PART + REQUEST_API_KEY_PART +
        REQUEST_LANGUAGE_PART + REQUEST_INCLUDE_ADULT + includeAdult.toString() + REQUEST_GENRE_ID + id.toString()
    }

    fun getMovieInfoRQ(id: Int): String {
        return REQUEST_BEGIN + REQUEST_MOVIE_PART + id.toString() + REQUEST_API_KEY_PART + REQUEST_LANGUAGE_PART
    }
}