package com.example.googlemapsapp.ui.composables.current_places

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.example.googlemapsapp.ui.composables.current_places.CurrentPlacesErrorScreen
import com.example.googlemapsapp.ui.composables.current_places.CurrentPlacesLoadingScreen
import com.example.googlemapsapp.ui.composables.current_places.CurrentPlacesSuccessScreen
import com.example.googlemapsapp.view_models.CurrentPlacesUiState
import com.example.googlemapsapp.view_models.CurrentPlacesViewModel

@Composable
fun CurrentPlacesScreen(
    viewModel: CurrentPlacesViewModel
) {
    when(val currentPlacesUiState = viewModel.currentPlacesUiState){
        is CurrentPlacesUiState.Success -> CurrentPlacesSuccessScreen(
            currentPlaces = currentPlacesUiState.places,
            viewModel = viewModel
        )
        is CurrentPlacesUiState.Loading -> CurrentPlacesLoadingScreen()
        is CurrentPlacesUiState.Error -> CurrentPlacesErrorScreen(
            statusCode = currentPlacesUiState.statusCode
        )
    }
}