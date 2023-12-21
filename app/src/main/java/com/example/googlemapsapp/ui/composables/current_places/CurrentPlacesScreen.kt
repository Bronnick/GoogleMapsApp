package com.example.googlemapsapp.ui.composables

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.example.googlemapsapp.ui.composables.current_places.CurrentPlacesErrorScreen
import com.example.googlemapsapp.ui.composables.current_places.CurrentPlacesLoadingScreen
import com.example.googlemapsapp.ui.composables.current_places.CurrentPlacesSuccessScreen
import com.example.googlemapsapp.view_models.CurrentPlacesUiState
import com.example.googlemapsapp.view_models.CurrentPlacesViewModel

@Composable
fun CurrentPlacesScreen(
    currentPlacesUiState: CurrentPlacesUiState
) {
    when(currentPlacesUiState){
        is CurrentPlacesUiState.Success -> CurrentPlacesSuccessScreen(
            currentPlaces = currentPlacesUiState.places
        )
        is CurrentPlacesUiState.Loading -> CurrentPlacesLoadingScreen()
        is CurrentPlacesUiState.Error -> CurrentPlacesErrorScreen(
            statusCode = currentPlacesUiState.statusCode
        )
    }
}