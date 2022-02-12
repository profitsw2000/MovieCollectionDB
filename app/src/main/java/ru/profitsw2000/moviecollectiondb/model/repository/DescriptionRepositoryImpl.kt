package ru.profitsw2000.moviecollectiondb.model.repository

import retrofit2.Callback
import ru.profitsw2000.moviecollectiondb.model.representation_tmdb.DescriptionDTO
import ru.profitsw2000.moviecollectiondb.model.representation_tmdb.PeopleDTO
import ru.profitsw2000.moviecollectiondb.retrofit.RemoteDataSource

class DescriptionRepositoryImpl (private val remoteDataSource: RemoteDataSource) : DescriptionRepository {

    override fun getMovieDescriptionFromServer(id: Int, callback: Callback<DescriptionDTO>) {
        remoteDataSource.getMovieDescription(id, callback)
    }

    override fun getPeoplesFromServer(personName: String, callback: Callback<PeopleDTO>) {
        remoteDataSource.getActorSearchResult(personName, callback)
    }

}