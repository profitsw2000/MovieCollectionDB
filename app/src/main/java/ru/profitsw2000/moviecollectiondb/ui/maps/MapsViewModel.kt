package ru.profitsw2000.moviecollectiondb.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.profitsw2000.moviecollectiondb.model.AppState
import ru.profitsw2000.moviecollectiondb.model.repository.DescriptionRepository
import ru.profitsw2000.moviecollectiondb.model.repository.DescriptionRepositoryImpl
import ru.profitsw2000.moviecollectiondb.model.representation_tmdb.PeopleDTO
import ru.profitsw2000.moviecollectiondb.retrofit.RemoteDataSource
import ru.profitsw2000.moviecollectiondb.utils.convertPeopleDTOToModel

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class MapsViewModel(private val descriptionRepositoryImpl: DescriptionRepository =
                        DescriptionRepositoryImpl(RemoteDataSource())
) : ViewModel() {
    private val localLiveData: MutableLiveData<AppState> = MutableLiveData()
    val mapLiveData: LiveData<AppState>
        get() {
        return localLiveData
    }

    fun getPeople(personName: String) = loadData(personName)

    private fun loadData(personName: String) {
        localLiveData.value = AppState.Loading
        descriptionRepositoryImpl.getPeoplesFromServer(personName, callBack)
    }

    private val callBack = object :
        Callback<PeopleDTO> {
        override fun onResponse(call: Call<PeopleDTO>, response:
        Response<PeopleDTO>
        ) {
            val serverResponse: PeopleDTO? = response.body()
            localLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(SERVER_ERROR)
                }
            )
        }

        override fun onFailure(call: Call<PeopleDTO>, t: Throwable) {
            localLiveData.postValue(AppState.Error(REQUEST_ERROR))
        }

        private fun checkResponse(serverResponse: PeopleDTO): AppState {
            with(serverResponse){
                return if (results == null) {
                    AppState.Error(CORRUPTED_DATA)
                } else {
                    AppState.ActorSuccess(convertPeopleDTOToModel(serverResponse))
                }
            }
        }
    }
}