package ru.profitsw2000.moviecollectiondb.model.Factory

import ru.profitsw2000.moviecollectiondb.model.representation.Category
import ru.profitsw2000.moviecollectiondb.model.representation.Movie
import java.util.*

object CategoriesFactory {
    private val random = Random()

    private val titles =  arrayListOf( "Комедии", "Мультфильмы", "Ужасы", "Фантастика", "Триллеры", "Боевики", "Мелодрамы", "Драмы")

    private fun randomTitle() : String{
        val index = random.nextInt(titles.size)
        return titles[index]
    }

    private fun randomMovies() : List<Movie>{
        return MoviesFactory.getMovies(20)
    }

    fun getCategories(count : Int) : List<Category>{
        val categories = mutableListOf<Category>()
        repeat(count){
            val category = Category(randomTitle(), randomMovies())
            categories.add(category)
        }
        return categories
    }
}