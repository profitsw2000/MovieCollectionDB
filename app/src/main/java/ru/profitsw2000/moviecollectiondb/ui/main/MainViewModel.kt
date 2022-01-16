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

    private fun getDataFromLocalSource() {
        val randomNumber = (0..5).random()

        liveData.value = AppState.Loading
        Thread {
            sleep(3000)
            if (randomNumber < 4) {
                liveData.postValue(AppState.Success(repository.getMovieFromLocalStorage()))
            } else {
            }
        }.start()
    }


}