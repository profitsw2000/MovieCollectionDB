package ru.profitsw2000.moviecollectiondb.model.representation

data class Category(
    val title: String,
    val movieList: List<Movie>
)
