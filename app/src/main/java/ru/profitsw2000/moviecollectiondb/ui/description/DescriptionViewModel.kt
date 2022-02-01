package ru.profitsw2000.moviecollectiondb.ui.description

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.profitsw2000.moviecollectiondb.model.AppState
import ru.profitsw2000.moviecollectiondb.model.repository.DescriptionRepository
import ru.profitsw2000.moviecollectiondb.model.repository.DescriptionRepositoryImpl
import ru.profitsw2000.moviecollectiondb.model.representation_tmdb.DescriptionDTO
import ru.profitsw2000.moviecollectiondb.retrofit.RemoteDataSource
import ru.profitsw2000.moviecollectiondb.utils.convertDescriptionDTOToModel

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class DescriptionViewModel(private val descriptionRepositoryImpl: DescriptionRepository =
    DescriptionRepositoryImpl(RemoteDataSource())) : ViewModel() {
    private val localLiveData: MutableLiveData<AppState> = MutableLiveData()
    val movieLiveData: LiveData<AppState> get() {
        return localLiveData
    }

    fun getMovieDescription(id: Int) = loadData(id)

    private fun loadData(id: Int) {
        localLiveData.value = AppState.Loading
        descriptionRepositoryImpl.getMovieDescriptionFromServer(id, callBack)
        //Thread{
            //val data = repository.getMovieFromServer(id)
            //localLiveData.postValue(AppState.MovieSuccess(data))
        //}.start()
    }

    private val callBack = object :
        Callback<DescriptionDTO> {
        override fun onResponse(call: Call<DescriptionDTO>, response:
        Response<DescriptionDTO>
        ) {
            val serverResponse: DescriptionDTO? = response.body()
            localLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(SERVER_ERROR)
                }
            )
        }

        override fun onFailure(call: Call<DescriptionDTO>, t: Throwable) {
            localLiveData.postValue(AppState.Error(REQUEST_ERROR))
        }

        private fun checkResponse(serverResponse: DescriptionDTO): AppState {
            with(serverResponse){
                return if (budget == null || genres == null || id == null ||
                    overview.isNullOrEmpty() || release_date.isNullOrEmpty() ||
                        revenue == null || runtime == null || title.isNullOrEmpty() ||
                        vote_average == null) {
                    AppState.Error(CORRUPTED_DATA)
                } else {
                    AppState.MovieSuccess(convertDescriptionDTOToModel(serverResponse))
                }
            }
        }
    }
}