package ru.profitsw2000.moviecollectiondb.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.profitsw2000.moviecollectiondb.model.AppState
import ru.profitsw2000.moviecollectiondb.model.repository.Repository
import java.lang.Thread.sleep

class MainViewModel (private val repository: Repository) : ViewModel() {
    private val liveData = MutableLiveData<AppState>()

    fun getLiveData(): LiveData<AppState> = liveData

    fun getMovie() = getDataFromLocalSource()

    fun getMovieInfo() = getDataFromServer()

    private fun getDataFromLocalSource() {
        val randomNumber = (0..5).random()

        liveData.value = AppState.Loading
        Thread {
            sleep(1000)
            if (randomNumber < 5) {
                liveData.postValue(AppState.Success(repository.getCategoriesFromLocalStorage()))
            } else {
                liveData.postValue(AppState.Error("Ошибка загрузки!!!"))
            }
        }.start()
    }

    private fun getDataFromServer() {
        val request = "https://api.themoviedb.org/3/genre/movie/list?api_key=c653b216d7d09c4aa4176e651f1ac4dd&language=ru"

        liveData.value = AppState.Loading
        Thread {
            liveData.postValue(AppState.Success(repository.getCategoriesFromServer(request)))
        }.start()
    }


}