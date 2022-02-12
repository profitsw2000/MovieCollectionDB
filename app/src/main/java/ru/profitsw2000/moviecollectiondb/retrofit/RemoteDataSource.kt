package ru.profitsw2000.moviecollectiondb.retrofit

import android.location.GnssMeasurementsEvent
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.converter.gson.GsonConverterFactory
import ru.profitsw2000.moviecollectiondb.BuildConfig
import ru.profitsw2000.moviecollectiondb.model.representation_tmdb.ActorDTO
import ru.profitsw2000.moviecollectiondb.model.representation_tmdb.DescriptionDTO
import ru.profitsw2000.moviecollectiondb.model.representation_tmdb.PeopleDTO

class RemoteDataSource {

    private val movieAPI = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build().create(MovieAPI::class.java)

    fun getMovieDescription(id: Int, callback: Callback<DescriptionDTO>) {
        movieAPI.getMovie(id, BuildConfig.MOVIE_API_KEY, "ru").enqueue(callback)
    }

    fun getActorSearchResult(personName: String, callback: Callback<PeopleDTO>) {
        movieAPI.getPeople(BuildConfig.MOVIE_API_KEY, personName).enqueue(callback)
    }

    fun getActorDetailsByID(id: Int, callback: Callback<ActorDTO>) {
        movieAPI.getActor(id, BuildConfig.MOVIE_API_KEY).enqueue(callback)
    }
}