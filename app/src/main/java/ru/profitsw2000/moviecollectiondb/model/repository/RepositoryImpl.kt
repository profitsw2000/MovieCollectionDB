package ru.profitsw2000.moviecollectiondb.model.repository

import ru.profitsw2000.moviecollectiondb.model.Factory.CategoriesFactory.getCategories
import ru.profitsw2000.moviecollectiondb.model.Factory.MoviesFactory.getMovies
import ru.profitsw2000.moviecollectiondb.model.GenresLoader
import ru.profitsw2000.moviecollectiondb.model.representation.Category

/**
 *
 */
class RepositoryImpl : Repository {
    val API_KEY = "c653b216d7d09c4aa4176e651f1ac4dd"

    override fun getCategoriesFromServer(request: String): List<Category> {
        var categories: ArrayList<Category> = arrayListOf()
        val dto = GenresLoader.loadGenres(request)

        var i = dto?.genres?.size

        if (dto != null) {
            if (i != null) {
                var j = 0
                while (i > 0) {
                    categories.add(Category(
                        dto.genres?.get(j)?.id ?: 0,dto.genres?.get(j)?.name ?: "Неизвестно",
                        getMovies(20)))
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