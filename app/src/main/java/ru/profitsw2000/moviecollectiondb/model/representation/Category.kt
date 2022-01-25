package ru.profitsw2000.moviecollectiondb.model.representation

data class Category(
    var id: Int,
    var title: String,
    var movieList: List<Movie>
)
