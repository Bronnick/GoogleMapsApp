package com.example.googlemapsapp.ui.composables.current_places

import androidx.compose.runtime.Composable
import com.example.googlemapsapp.classes.Place
import com.example.googlemapsapp.view_models.CurrentPlacesUiState
import com.example.googlemapsapp.view_models.CurrentPlacesViewModel

@Composable
fun CurrentPlacesScreen(
    viewModel: CurrentPlacesViewModel,
    onShowOnMapButtonClick: (Place) -> Unit,
    onViewDetailsButtonClick: (Place) -> Unit,
) {

    when(val currentPlacesUiState = viewModel.currentPlacesUiState){
        is CurrentPlacesUiState.Success -> CurrentPlacesSuccessScreen(
            viewModel = viewModel,
            onShowOnMapButtonClick = onShowOnMapButtonClick,
            onViewDetailsButtonClick = onViewDetailsButtonClick
        )
        is CurrentPlacesUiState.Loading -> CurrentPlacesLoadingScreen()
        is CurrentPlacesUiState.Error -> CurrentPlacesErrorScreen(
            statusCode = currentPlacesUiState.statusCode
        )
    }
}