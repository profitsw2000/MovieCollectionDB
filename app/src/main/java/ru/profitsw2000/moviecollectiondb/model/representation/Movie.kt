package ru.profitsw2000.moviecollectiondb.model.representation

import java.util.*

data class Movie(
    val title: String,
    val genre: String,
    val duration: Int,
    val rating: Float,
    val budget: Int,
    val revenue: Int,
    val releaseDate: String,
    val description: String,
    val picture: Int
)
