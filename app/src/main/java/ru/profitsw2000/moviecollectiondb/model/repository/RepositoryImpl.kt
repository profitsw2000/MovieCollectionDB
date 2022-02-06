package ru.profitsw2000.moviecollectiondb.model.repository

import ru.profitsw2000.moviecollectiondb.model.Factory.CategoriesFactory.getCategories
import ru.profitsw2000.moviecollectiondb.model.Factory.MoviesFactory.getMovies
import ru.profitsw2000.moviecollectiondb.model.representation.Category
import ru.profitsw2000.moviecollectiondb.model.representation.Movie
import ru.profitsw2000.moviecollectiondb.model.representation_tmdb.DiscoverDTO
import ru.profitsw2000.moviecollectiondb.model.representation_tmdb.GenresDTO
import ru.profitsw2000.moviecollectiondb.model.GenresLoader as DTOLoader1

/**
 *
 */
class RepositoryImpl : Repository {

    override fun getMovieFromServer(id: Int): Movie

    override fun getMoviesFromServer(id: Int){
        val movies: ArrayList<Movie>
        val request = "https://api.themoviedb.org/3/discover/movie?api_key=c653b216d7d09c4aa4176e651f1ac4dd&language=ru&sort_by=popularity.desc&page=1&with_genres=" + id.toString
        val loader: DTOLoader1<DiscoverDTO>

        val dto = loader.loadDTO(request)
        var i = dto.size
        while(i >= 0){
            movies[i].title = dto.results[i]?.title?
            i--
        }
        return movies
    }

    override fun getCategoriesFromServer(request: String) {
        val request = "https://api.themoviedb.org/3/genre/movie/list?api_key=c653b216d7d09c4aa4176e651f1ac4dd&language=ru"
        val categories: ArrayList<Category>
        val loader: DTOLoader1<GenresDTO>

        val dto = loader.loadDTO(request)
        var i = dto.size
        while(i >= 0){
            categories[i].title = genres[i]?.name?
            categories[i].id = genres[i]?.id?
            categories[i].movieList = getMoviesFromServer
            i--
        }
        return categories
    }
    override fun getMoviesFromLocalStorage() = getMovies(20)
    override fun getCategoriesFromLocalStorage() = getCategories(20)
}