package com.example.googlemapsapp.view_models

import androidx.lifecycle.ViewModel
import com.example.googlemapsapp.classes.Place
import com.example.googlemapsapp.repositories.AppSettingsRepository
import com.example.googlemapsapp.repositories.PlacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
    private val placesRepository: PlacesRepository
) : ViewModel() {
    val favoritePlaces: Flow<List<Place>> =
        placesRepository.getFavoritePlaces()


}