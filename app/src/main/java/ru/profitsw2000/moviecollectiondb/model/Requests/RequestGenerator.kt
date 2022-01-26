package ru.profitsw2000.moviecollectiondb.model.Requests


private const val API_KEY = "c653b216d7d09c4aa4176e651f1ac4dd"

private const val REQUEST_BEGIN = "https://api.themoviedb.org/3/"
private const val REQUEST_GENRE_PART = "genre/movie/list"
private const val REQUEST_GENRE_ID = "&page=1&with_genres="
private const val REQUEST_DISCOVERY_PART = "discover/movie"
private const val REQUEST_MOVIE_PART = "movie/"
private const val REQUEST_LANGUAGE_PART = "&language=ru"
private const val REQUEST_API_KEY_PART = "?api_key=$API_KEY"

object RequestGenerator {

    fun getGenresListRQ(): String {
        return REQUEST_BEGIN + REQUEST_GENRE_PART + REQUEST_API_KEY_PART + REQUEST_LANGUAGE_PART
    }

    fun getMovieListByGenreRQ(id: Int): String {
        return REQUEST_BEGIN + REQUEST_DISCOVERY_PART + REQUEST_API_KEY_PART + REQUEST_LANGUAGE_PART + REQUEST_GENRE_ID + id.toString()
    }

    fun getMovieInfoRQ(id: Int): String {
        return REQUEST_BEGIN + REQUEST_MOVIE_PART + id.toString() + REQUEST_API_KEY_PART + REQUEST_LANGUAGE_PART
    }
  /*
    private
    val request = "https://api.themoviedb.org/3/genre/movie/list?api_key=c653b216d7d09c4aa4176e651f1ac4dd&language=ru"

    val requestMovieList = "https://api.themoviedb.org/3/discover/movie?api_key=c653b216d7d09c4aa4176e651f1ac4dd&language=ru&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_genres="

    val requestMovieInfo = "https://api.themoviedb.org/3/movie/" +
            id.toString() + "?api_key=c653b216d7d09c4aa4176e651f1ac4dd&language=ru"
*/
}