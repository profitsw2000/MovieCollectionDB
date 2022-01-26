package ru.profitsw2000.moviecollectiondb.ui.description

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.profitsw2000.moviecollectiondb.model.AppState
import ru.profitsw2000.moviecollectiondb.model.repository.Repository

class DescriptionViewModel(private val repository: Repository) : ViewModel() {
    private val localLiveData: MutableLiveData<AppState> = MutableLiveData()
    val movieLiveData: LiveData<AppState> get() {
        return localLiveData
    }

    fun loadData(id: Int) {
        localLiveData.value = AppState.Loading
        Thread{
            val data = repository.getMovieFromServer(id)
            localLiveData.postValue(AppState.MovieSuccess(data))
        }.start()
    }
}