package com.example.googlemapsapp.view_models

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.googlemapsapp.classes.Place
import com.example.googlemapsapp.repositories.AppSettingsRepository
import com.example.googlemapsapp.repositories.PlacesRepository
import com.example.googlemapsapp.utils.maxCurrentPlacesNumberParam
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.PlaceLikelihood
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


sealed interface CurrentPlacesUiState{
    data class Success(
        val places: Flow<List<Place>>,
    ) : CurrentPlacesUiState
    object Loading : CurrentPlacesUiState

    data class Error(
        val statusCode: Int
    ) : CurrentPlacesUiState
}

@HiltViewModel
class CurrentPlacesViewModel @Inject constructor(
    private val currentPlacesRepository: PlacesRepository,
    private val settingsRepository: AppSettingsRepository
) : ViewModel() {

    var currentPlacesUiState: CurrentPlacesUiState by mutableStateOf(CurrentPlacesUiState.Loading)
        private set

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    private val placesList = ArrayList<Place>()

    private var maxCurrentPlacesNumber: Int = 10

    init {
        refresh()
    }

    fun refreshList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                placesList.forEach { place ->
                    place.isFavorite = currentPlacesRepository.getPlaceById(place.placeId) != null
                }

                val outerList = ArrayList<List<Place>>()
                outerList.add(placesList)
                currentPlacesUiState = CurrentPlacesUiState.Success(outerList.asFlow())
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            currentPlacesUiState = CurrentPlacesUiState.Loading
            maxCurrentPlacesNumber = settingsRepository.getParameterByKey(
                maxCurrentPlacesNumberParam
            ) as? Int ?: 10
            getCurrentPlaces()
            /*favoritePlaces.collect {
                for (item in it) {
                    for (el in placesList) {
                        if (el.placeId == item.placeId) {
                            el.isFavorite = true
                            Log.d("myLogs", "Place: ${item.placeId} ${el.placeId}")
                        }
                    }
                }
            }*/
        }
    }

    private suspend fun getCurrentPlaces(){
        placesList.clear()
        val placeResponse = currentPlacesRepository.getCurrentPlaces()
        placeResponse.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val response = task.result

                val placeLikelihoods = response?.placeLikelihoods
                viewModelScope.launch {
                    for (placeLikelihood in placeLikelihoods ?: emptyList()) {
                        withContext(Dispatchers.IO) {
                            var isFavorite = false
                            if (currentPlacesRepository.getPlaceById(placeLikelihood.place.id!!) != null) {
                                isFavorite = true
                            }

                            if(placesList.size < maxCurrentPlacesNumber) {
                                placesList.add(
                                    Place(
                                        placeId = placeLikelihood.place.id ?: "",
                                        name = placeLikelihood.place.name ?: "undefined",
                                        likelihood = placeLikelihood.likelihood,
                                        latitude = placeLikelihood.place.latLng?.latitude
                                            ?: 0.0,
                                        longitude = placeLikelihood.place.latLng?.longitude
                                            ?: 0.0,
                                        photoRef = placeLikelihood.place.photoMetadatas?.get(0)
                                            ?.zza(),
                                        address = placeLikelihood.place.address,
                                        rating = placeLikelihood.place.rating,
                                        isFavorite = isFavorite
                                    )
                                )
                            }
                        }
                    }
                }

                val outerList = ArrayList<List<Place>>()
                outerList.add(placesList)
                currentPlacesUiState = CurrentPlacesUiState.Success(outerList.asFlow())
            } else {
                val exception = task.exception
                if (exception is ApiException) {
                    currentPlacesUiState = CurrentPlacesUiState.Error(exception.statusCode)
                }
            }
            _isRefreshing.value = false
        }
    }

    fun changePlaceFavoriteStatus(place: Place) {
        /*try{
            (currentPlacesUiState as CurrentPlacesUiState.Success).places.
        }*/
        place.isFavorite = !place.isFavorite

        if(place.isFavorite) {
            viewModelScope.launch {
                currentPlacesRepository.insertPlace(place)
            }
        } else{
            viewModelScope.launch {
                currentPlacesRepository.deletePlace(place)
            }
        }
    }
}