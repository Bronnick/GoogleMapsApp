package com.example.googlemapsapp.view_models

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlemapsapp.repositories.PlacesRepository
import com.example.googlemapsapp.classes.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritePlacesViewModel @Inject constructor(
    private val placesRepository: PlacesRepository
) : ViewModel() {

    val favoritePlaces: Flow<List<Place>> =
        placesRepository.getFavoritePlaces()

    fun deletePlace(place: Place){
        viewModelScope.launch {
            changePlaceFavoriteStatus(place)
        }
    }

    fun changePlaceFavoriteStatus(place: Place) {
        /*try{
            (currentPlacesUiState as CurrentPlacesUiState.Success).places.
        }*/
        var placeVar = place
        placeVar = place.copy(isFavorite = !place.isFavorite)
        Log.d("myLogs", "Place: ${place.name}, ${place.isFavorite}")

        if(placeVar.isFavorite) {
            viewModelScope.launch {
                placesRepository.insertPlace(placeVar)
            }
        } else{
            viewModelScope.launch {
                placesRepository.deletePlace(placeVar)
            }
        }
    }
}

