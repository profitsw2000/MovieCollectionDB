package ru.profitsw2000.moviecollectiondb.model.repository

import retrofit2.Callback
import ru.profitsw2000.moviecollectiondb.model.representation_tmdb.DescriptionDTO
import ru.profitsw2000.moviecollectiondb.model.representation_tmdb.PeopleDTO

interface DescriptionRepository {
    fun getMovieDescriptionFromServer(id: Int, callback: retrofit2.Callback<DescriptionDTO>)
    fun getPeoplesFromServer(personName: String, callback: Callback<PeopleDTO>)
}