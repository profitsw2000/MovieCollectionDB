package ru.profitsw2000.moviecollectiondb.services

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import ru.profitsw2000.moviecollectiondb.model.MovieLoader
import ru.profitsw2000.moviecollectiondb.model.Requests.RequestGenerator
import ru.profitsw2000.moviecollectiondb.model.representation_tmdb.DescriptionDTO
import ru.profitsw2000.moviecollectiondb.model.representation_tmdb.GroupDTO
import ru.profitsw2000.moviecollectiondb.ui.description.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val MOVIE_ID_EXTRA = "Movie_id"
private const val REQUEST_GET = "GET"
private const val REQUEST_TIMEOUT = 10000

class DescriptionService : IntentService("DescriptionService") {

    private val broadcastIntent = Intent(DESCRIPTION_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            onEmptyIntent()
        } else {
            val id = intent.getIntExtra(MOVIE_ID_EXTRA, 0)
            if (id == 0) {
                onEmptyData()
            } else {
                loadMovie(id)
            }
        }
    }

    //@RequiresApi(Build.VERSION_CODES.N)
    private fun loadMovie(id: Int){
        try {
            val uri = URL(RequestGenerator.getMovieInfoRQ(id))

            lateinit var urlConnection: HttpsURLConnection

            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = REQUEST_GET
                urlConnection.readTimeout = REQUEST_TIMEOUT
                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                // преобразование ответа от сервера (JSON) в модель данных
                val lines = if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    getLinesForOld(bufferedReader)
                } else {
                    getLines(bufferedReader)
                }

                val movieDescriptionDTO: DescriptionDTO = Gson().fromJson(lines, DescriptionDTO::class.java)
                onResponse(movieDescriptionDTO)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
    }

    private fun onResponse(movieDescriptionDTO: DescriptionDTO) {
        val title = movieDescriptionDTO.title
        if (title == null) {
            onEmptyResponse()
        } else {
            onSuccessResponse(movieDescriptionDTO.budget,
                movieDescriptionDTO.genres,
                movieDescriptionDTO.id,
                movieDescriptionDTO.overview,
                movieDescriptionDTO.release_date,
                movieDescriptionDTO.revenue,
                movieDescriptionDTO.runtime,
                movieDescriptionDTO.title,
                movieDescriptionDTO.vote_average
            )
        }
    }

    private fun onSuccessResponse(
        budget: Int?,
        genres: List<GroupDTO>?,
        id: Int?,
        overview: String?,
        releaseDate: String?,
        revenue: Int?,
        runtime: Int?,
        title: String,
        voteAverage: Float?
    ) {
        putLoadResult(DESCRIPTION_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putExtra(DESCRIPTION_BUDGET_EXTRA, budget)
        broadcastIntent.putExtra(DESCRIPTION_GENRES_EXTRA, getStringFromGenresArray(genres))
        broadcastIntent.putExtra(DESCRIPTION_ID_EXTRA, id)
        broadcastIntent.putExtra(DESCRIPTION_OVERVIEW_EXTRA, overview)
        broadcastIntent.putExtra(DESCRIPTION_RELEASE_DATE_EXTRA, releaseDate)
        broadcastIntent.putExtra(DESCRIPTION_REVENUE_EXTRA, revenue)
        broadcastIntent.putExtra(DESCRIPTION_RUNTIME_EXTRA, runtime)
        broadcastIntent.putExtra(DESCRIPTION_TITLE_EXTRA, title)
        broadcastIntent.putExtra(DESCRIPTION_VOTE_AVERAGE_EXTRA, voteAverage)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(descriptionResponseSuccessExtra: String) {
        broadcastIntent.putExtra(DESCRIPTION_LOAD_RESULT_EXTRA, descriptionResponseSuccessExtra)
    }

    private fun getStringFromGenresArray(genres: List<GroupDTO>?): String{
        var genresList: ArrayList<String> = arrayListOf()
        var i = genres?.size

        //get string with genres of movie from List of strings
        if (i != null) {
            var j = 0
            while (i > 0) {
                genresList.add(genres?.get(j)?.name ?: "undefined")
                i--
                j++
            }
        }
        val genres = genresList.joinToString(separator = ", ")

        return genres
    }

    private fun onEmptyResponse() {
        putLoadResult(DESCRIPTION_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyData() {
        putLoadResult(DESCRIPTION_DATA_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyIntent(){
        putLoadResult(DESCRIPTION_INTENT_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun getLinesForOld(reader: BufferedReader): String {
        val rawData = StringBuilder(1024)
        var tempVariable: String?

        while (reader.readLine().also { tempVariable = it } != null) {
            rawData.append(tempVariable).append("\n")
        }

        reader.close()
        return rawData.toString()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

}