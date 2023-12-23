package com.example.googlemapsapp.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.googlemapsapp.repositories.PlacesRepository
import com.example.googlemapsapp.classes.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FavouritePlacesViewModel @Inject constructor(
    placesRepository: PlacesRepository
) : ViewModel() {

    val favoritePlaces: Flow<List<Place>> =
        placesRepository.getFavoritePlaces()

}

