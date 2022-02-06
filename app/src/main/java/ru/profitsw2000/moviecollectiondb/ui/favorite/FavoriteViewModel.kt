package ru.profitsw2000.moviecollectiondb.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.profitsw2000.moviecollectiondb.App
import ru.profitsw2000.moviecollectiondb.model.AppState
import ru.profitsw2000.moviecollectiondb.model.repository.LocalFavRepository
import ru.profitsw2000.moviecollectiondb.model.repository.LocalFavRepositoryImpl
import ru.profitsw2000.moviecollectiondb.model.repository.LocalRepository
import ru.profitsw2000.moviecollectiondb.model.repository.LocalRepositoryImpl

class FavoriteViewModel (private val favoriteRepository: LocalFavRepository = LocalFavRepositoryImpl(App.getFavoriteDao())
) : ViewModel() {

    private val favoriteLiveData: MutableLiveData<AppState> = MutableLiveData()

    val favoriteMovieLiveData: LiveData<AppState>
        get() {
        return favoriteLiveData
    }

    fun getFavoriteMovies() {
        favoriteLiveData.value = AppState.Loading
        viewModelScope.launch { favoriteLiveData.postValue(AppState.FavoriteSuccess(favoriteRepository.getAllFavoriteMovies()))  }
        //noteLiveData.value = AppState.Error("Nothing")
    }
}