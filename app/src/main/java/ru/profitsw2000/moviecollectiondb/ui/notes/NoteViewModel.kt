package ru.profitsw2000.moviecollectiondb.ui.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.profitsw2000.moviecollectiondb.App
import ru.profitsw2000.moviecollectiondb.App.Companion.getNoteDao
import ru.profitsw2000.moviecollectiondb.model.AppState
import ru.profitsw2000.moviecollectiondb.model.repository.LocalRepository
import ru.profitsw2000.moviecollectiondb.model.repository.LocalRepositoryImpl

class NoteViewModel (private val noteRepository: LocalRepository = LocalRepositoryImpl(App.getNoteDao())
    ) : ViewModel() {

    private val noteLiveData: MutableLiveData<AppState> = MutableLiveData()

    val movieNoteLiveData: LiveData<AppState> get() {
        return noteLiveData
    }

    fun getMovieNotes() {
        noteLiveData.value = AppState.Loading
        viewModelScope.launch { noteLiveData.postValue(AppState.NotesSuccess(noteRepository.getAllNotes()))  }
        //noteLiveData.value = AppState.Error("Nothing")
    }
}