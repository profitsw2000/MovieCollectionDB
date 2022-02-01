package ru.profitsw2000.moviecollectiondb.retrofit

import android.location.GnssMeasurementsEvent
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.converter.gson.GsonConverterFactory
import ru.profitsw2000.moviecollectiondb.BuildConfig
import ru.profitsw2000.moviecollectiondb.model.representation_tmdb.DescriptionDTO

class RemoteDataSource {
    private val movieAPI = Retrofit.Builder()
        .baseUrl("https://api.weather.yandex.ru/")
        .addConverterFactory(GsonConverterFactory.create(
            GsonBuilder().setLenient().create()
            )
        )
        .build().create(MovieAPI::class.java)
    fun getMovieDescription(id: Int, apiKey: String, language: String, callback: Callback<DescriptionDTO>) {
        movieAPI.getMovie(id, BuildConfig.MOVIE_API_KEY, language).enqueue(callback)
    }
}