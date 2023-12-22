package com.example.googlemapsapp.view_models

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.googlemapsapp.classes.CurrentPlace
import com.example.googlemapsapp.repositories.CurrentPlacesRepository
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.PlaceLikelihood
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface CurrentPlacesUiState{
    data class Success(
        val places: List<CurrentPlace>
    ) : CurrentPlacesUiState
    object Loading : CurrentPlacesUiState

    data class Error(
        val statusCode: Int
    ) : CurrentPlacesUiState
}

@HiltViewModel
class CurrentPlacesViewModel @Inject constructor(
    private val currentPlacesRepository: CurrentPlacesRepository
) : ViewModel() {
    var currentPlacesUiState: CurrentPlacesUiState by mutableStateOf(CurrentPlacesUiState.Loading)
        private set
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
                        "Place '${placeLikelihood.place.name}' has likelihood: ${placeLikelihood.likelihood}"
                    )
                    Log.i("myLogs", "photo list size: ${placeLikelihood.place.photoMetadatas?.size}")
                    for(photo in placeLikelihood.place.photoMetadatas ?: emptyList()){
                        Log.i("myLogs","Photo: ${photo.attributions
                            .substringAfter('"').substringBefore('"')}")
                    }
                }
                val placeLikelihoods = response?.placeLikelihoods
                val placesList = ArrayList<CurrentPlace>()
                for(placeLikelihood in placeLikelihoods ?: emptyList()){
                    placesList.add(
                        CurrentPlace(
                            name = placeLikelihood.place.name ?: "undefined",
                            likelihood = placeLikelihood.likelihood,
                            photos = placeLikelihood.place.photoMetadatas ?: emptyList()
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
}