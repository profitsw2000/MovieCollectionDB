package ru.profitsw2000.moviecollectiondb.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.profitsw2000.moviecollectiondb.model.AppState
import ru.profitsw2000.moviecollectiondb.model.Requests.RequestGenerator
import ru.profitsw2000.moviecollectiondb.model.repository.Repository
import java.lang.Thread.sleep

class MainViewModel (private val repository: Repository) : ViewModel() {
    private val liveData = MutableLiveData<AppState>()

    fun getLiveData(): LiveData<AppState> = liveData

    fun getMovie() = getDataFromLocalSource()

    fun getMovieInfo(includeAdult: Boolean) = getDataFromServer(includeAdult)

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

    private fun getDataFromServer(includeAdult: Boolean) {
        val request = RequestGenerator.getGenresListRQ()

        liveData.value = AppState.Loading
        Thread {
            val categories = repository.getCategoriesFromServer(request, includeAdult)

            if (categories != null && categories.isNotEmpty()) {
                liveData.postValue(AppState.Success(categories))
            } else {
                liveData.postValue(AppState.Error("Ошибка загрузки!!!"))
            }
        }.start()
    }


}