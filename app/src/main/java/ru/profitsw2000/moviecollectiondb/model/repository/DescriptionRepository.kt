package ru.profitsw2000.moviecollectiondb.model.repository

import ru.profitsw2000.moviecollectiondb.model.representation_tmdb.DescriptionDTO

interface DescriptionRepository {
    fun getMovieDescriptionFromServer(id: Int, callback: retrofit2.Callback<DescriptionDTO>)
}