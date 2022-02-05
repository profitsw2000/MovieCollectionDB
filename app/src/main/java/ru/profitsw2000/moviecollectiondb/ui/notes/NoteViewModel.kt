package ru.profitsw2000.moviecollectiondb.ui.notes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.profitsw2000.moviecollectiondb.App
import ru.profitsw2000.moviecollectiondb.App.Companion.getNoteDao
import ru.profitsw2000.moviecollectiondb.model.AppState
import ru.profitsw2000.moviecollectiondb.model.repository.LocalRepository
import ru.profitsw2000.moviecollectiondb.model.repository.LocalRepositoryImpl

class NoteViewModel (private val noteRepository: LocalRepository = LocalRepositoryImpl(App.getNoteDao())
    ) : ViewModel() {

        private val noteLiveData: MutableLiveData<AppState> = MutableLiveData()

        suspend fun getMovieNotes() {
            noteLiveData.value = AppState.Loading
            noteLiveData.value = AppState.NotesSuccess(noteRepository.getAllNotes())
        }
}