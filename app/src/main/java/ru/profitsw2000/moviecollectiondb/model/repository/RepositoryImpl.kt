package ru.profitsw2000.moviecollectiondb.model.repository

import ru.profitsw2000.moviecollectiondb.R
import ru.profitsw2000.moviecollectiondb.model.Factory.CategoriesFactory.getCategories
import ru.profitsw2000.moviecollectiondb.model.Factory.MoviesFactory.getMovies
import ru.profitsw2000.moviecollectiondb.model.GenresLoader
import ru.profitsw2000.moviecollectiondb.model.MovieLoader
import ru.profitsw2000.moviecollectiondb.model.TitlesLoader
import ru.profitsw2000.moviecollectiondb.model.representation.Category
import ru.profitsw2000.moviecollectiondb.model.representation.Movie

/**
 *
 */
class RepositoryImpl : Repository {
    val API_KEY = "c653b216d7d09c4aa4176e651f1ac4dd"

    override fun getMovieFromServer(id: Int): Movie {
        val requestMovieInfo = "https://api.themoviedb.org/3/movie/" +
                id.toString() + "?api_key=c653b216d7d09c4aa4176e651f1ac4dd&language=ru"
        val dto = MovieLoader.loadMovie(requestMovieInfo)

        if (dto != null) {
            var genresList: ArrayList<String> = arrayListOf()
            var i = dto.genres?.size

            //get string with genres of movie from List of strings
            if (i != null) {
                var j = 0
                while (i > 0) {
                    genresList.add(dto.genres?.get(j)?.name ?: "undefined")
                    i--
                    j++
                }
            }
            val genres = genresList.joinToString(separator = ", ")

            return Movie(dto.title ?: "Undefined",
                            genres,
                            dto.runtime ?: 0,
                                (dto.vote_average ?: 0) as Float,
                            dto.budget ?: 0,
                            dto.revenue ?: 0,
                            dto.release_date ?: "1970-01-01",
                            dto.overview ?: "Undefined",
                                R.drawable.film,
                            dto.id ?: 0,
            )
        }
        return getMovies(1).get(0)
    }

    override fun getMoviesFromServer(request: String): List<Movie> {

        var movies: ArrayList<Movie> = arrayListOf()
        var dto = TitlesLoader.loadTitles(request)

        var i = dto?.results?.size

        if (dto != null) {
            if (i != null) {
                var j = 0
                while (i > 0) {
                    movies.add(Movie(title = dto.results?.get(j)?.title ?: "Неизвестно", id = dto.results?.get(j)?.id ?: 887767))
                    i--
                    j++
                }
            }
        }
        return movies
    }

    override fun getCategoriesFromServer(request: String): List<Category> {
        val requestMovieList = "https://api.themoviedb.org/3/discover/movie?api_key=c653b216d7d09c4aa4176e651f1ac4dd&language=ru&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_genres="
        var categories: ArrayList<Category> = arrayListOf()
        val dto = GenresLoader.loadGenres(request)

        var i = dto?.genres?.size

        if (dto != null) {
            if (i != null) {
                var j = 0
                while (i > 0) {
                    val id = dto.genres?.get(j)?.id ?: 0
                    val moviesList = getMoviesFromServer(requestMovieList + id.toString())
                    if (moviesList != null && moviesList.isNotEmpty()) {
                        categories.add(Category(
                            dto.genres?.get(j)?.id ?: 0,dto.genres?.get(j)?.name ?: "Неизвестно",
                            moviesList))
                    }
                    else {
                        categories.add(
                            Category(
                                dto.genres?.get(j)?.id ?: 0,
                                dto.genres?.get(j)?.name ?: "Неизвестно",
                                getMovies(20)
                            )
                        )
                    }
                    i--
                    j++
                }
            }
        }
        return categories
    }

    override fun getMoviesFromLocalStorage() = getMovies(20)
    override fun getCategoriesFromLocalStorage() = getCategories(20)
}