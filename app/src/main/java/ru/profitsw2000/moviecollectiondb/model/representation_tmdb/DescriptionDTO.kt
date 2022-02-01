package ru.profitsw2000.moviecollectiondb.model.representation_tmdb

import java.time.Duration

data class DescriptionDTO(
    val budget: Int?,
    val genres: List<GroupDTO>?,
    val id: Int?,
    val overview: String?,
    val poster_path: String?,
    val release_date: String?,
    val revenue: Int?,
    val runtime: Int?,
    val title: String?,
    val vote_average: Float?
)
