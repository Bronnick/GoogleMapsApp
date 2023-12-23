package com.example.googlemapsapp.view_models

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import com.example.googlemapsapp.classes.Place
import com.example.googlemapsapp.repositories.PlacesRepository
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.PlaceLikelihood
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface CurrentPlacesUiState{
    data class Success(
        val places: List<Place>
    ) : CurrentPlacesUiState
    object Loading : CurrentPlacesUiState

    data class Error(
        val statusCode: Int
    ) : CurrentPlacesUiState
}

@HiltViewModel
class CurrentPlacesViewModel @Inject constructor(
    private val currentPlacesRepository: PlacesRepository
) : ViewModel() {
    var currentPlacesUiState: CurrentPlacesUiState by mutableStateOf(CurrentPlacesUiState.Loading)
        private set

    var test: String by mutableStateOf("test")

    init{
        viewModelScope.launch {
            getCurrentPlaces()
        }
    }

    private fun getCurrentPlaces(){

        val placeResponse = currentPlacesRepository.getCurrentPlaces()
        placeResponse.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val response = task.result
                for (placeLikelihood: PlaceLikelihood in response?.placeLikelihoods
                    ?: emptyList()) {
                    Log.i(
                        "myLogs",
                        "Id ${placeLikelihood.place.id} of Place '${placeLikelihood.place.name}' has likelihood: ${placeLikelihood.likelihood}"
                    )
                    Log.i("myLogs", "photo list size: ${placeLikelihood.place.photoMetadatas?.size}")

                    for(photo in placeLikelihood.place.photoMetadatas ?: emptyList()){
                        Log.i("myLogs", "photo: ${photo.zza()}")
                    }
                }


                val placeLikelihoods = response?.placeLikelihoods

                val placesList = ArrayList<Place>()
                for(placeLikelihood in placeLikelihoods ?: emptyList()){

                    placesList.add(
                        Place(
                            placeId = placeLikelihood.place.id ?: "",
                            name = placeLikelihood.place.name ?: "undefined",
                            likelihood = placeLikelihood.likelihood,
                            photo = placeLikelihood.place.photoMetadatas?.get(0)?.zza(),
                            isFavorite = false
                        )
                    )
                }
                currentPlacesUiState = CurrentPlacesUiState.Success(placesList)
            } else {
                val exception = task.exception
                if (exception is ApiException) {
                    currentPlacesUiState = CurrentPlacesUiState.Error(exception.statusCode)
                    Log.e("myLogs", "Place not found: ${exception.statusCode}")
                }
            }
        }
    }

    fun changePlaceFavoriteStatus(place: Place) {
        /*try{
            (currentPlacesUiState as CurrentPlacesUiState.Success).places.
        }*/
        place.isFavorite = !place.isFavorite
        Log.d("myLogs", "Place: ${place.name}, ${place.isFavorite}")

        if(place.isFavorite) {
            viewModelScope.launch {
                currentPlacesRepository.insertPlace(place)
                val favoritePlaces = currentPlacesRepository.getFavoritePlaces()
                favoritePlaces.collect{
                    for(item in it){
                        Log.d("myLogs", "Place: ${item.name}")
                    }
                }
            }
        }
    }
}