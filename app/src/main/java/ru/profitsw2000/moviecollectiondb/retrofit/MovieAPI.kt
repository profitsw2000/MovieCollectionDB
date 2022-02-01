package ru.profitsw2000.moviecollectiondb.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import ru.profitsw2000.moviecollectiondb.model.representation_tmdb.DescriptionDTO

interface MovieAPI {
    @GET("movie/{movie_id}")
    fun getMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): Call<DescriptionDTO>
}