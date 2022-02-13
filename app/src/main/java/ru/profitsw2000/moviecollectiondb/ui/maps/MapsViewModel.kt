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
import ru.profitsw2000.moviecollectiondb.model.representation_tmdb.ActorDTO
import ru.profitsw2000.moviecollectiondb.model.representation_tmdb.PeopleDTO
import ru.profitsw2000.moviecollectiondb.retrofit.RemoteDataSource

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"
private const val EMPTY_RESULT = "Ничего не найдено"

class MapsViewModel(private val descriptionRepositoryImpl: DescriptionRepository =
                        DescriptionRepositoryImpl(RemoteDataSource())
) : ViewModel() {
    private val localLiveData: MutableLiveData<AppState> = MutableLiveData()
    val mapLiveData: LiveData<AppState>
        get() {
        return localLiveData
    }

    fun getPeople(personName: String) = loadData(personName)

    private fun getActorDetails(id: Int) {
        descriptionRepositoryImpl.getActorDetailsFromServer(id, actorCallBack)
    }

    private fun loadData(personName: String) {
        localLiveData.value = AppState.Loading
        descriptionRepositoryImpl.getPeoplesFromServer(personName, callBack)
    }

    private val callBack = object : Callback<PeopleDTO> {

        override fun onResponse(call: Call<PeopleDTO>, response: Response<PeopleDTO>) {
            val serverResponse: PeopleDTO? = response.body()

            if (response.isSuccessful && serverResponse != null) {
                checkResponse(serverResponse)
            } else {
                localLiveData.postValue(
                    AppState.Error(SERVER_ERROR)
                )
            }
        }

        override fun onFailure(call: Call<PeopleDTO>, t: Throwable) {
            localLiveData.postValue(AppState.Error(REQUEST_ERROR))
        }

        private fun checkResponse(serverResponse: PeopleDTO) {
            with(serverResponse){
                return if (results == null) {
                    localLiveData.postValue(
                        AppState.Error(CORRUPTED_DATA)
                    )
                } else if (results.size == 0) {
                    localLiveData.postValue(
                        AppState.Error(EMPTY_RESULT)
                    )
                }
                else {
                    for (result in serverResponse.results!!) {
                        if (result.id != null) getActorDetails(result.id)
                    }
                }
            }
        }
    }

    private val actorCallBack = object : Callback<ActorDTO> {

        override fun onResponse(call: Call<ActorDTO>, response: Response<ActorDTO>) {
            val serverResponse: ActorDTO? = response.body()
            localLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(SERVER_ERROR)
                }
            )
        }

        override fun onFailure(call: Call<ActorDTO>, t: Throwable) {
            localLiveData.postValue(AppState.Error(REQUEST_ERROR))
        }

        private fun checkResponse(serverResponse: ActorDTO): AppState {
            with(serverResponse){
                return if (also_known_as == null || place_of_birth == null) {
                    AppState.Error(CORRUPTED_DATA)
                } else {
                    AppState.ActorSuccess(serverResponse)
                }
            }
        }
    }
}