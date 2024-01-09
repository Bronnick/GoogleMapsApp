package com.example.googlemapsapp.view_models

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

    fun deletePlace(place: Place) {
        viewModelScope.launch {
            changePlaceFavoriteStatus(place)
        }
    }

    private fun changePlaceFavoriteStatus(place: Place) {
        /*try{
            (currentPlacesUiState as CurrentPlacesUiState.Success).places.
        }*/
        place.isFavorite = !place.isFavorite

        if (place.isFavorite) {
            viewModelScope.launch {
                placesRepository.insertPlace(place)
            }
        } else {
            viewModelScope.launch {
                placesRepository.deletePlace(place)
            }
        }
    }
}

