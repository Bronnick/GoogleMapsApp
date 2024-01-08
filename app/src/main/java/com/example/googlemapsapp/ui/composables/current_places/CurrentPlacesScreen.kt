package com.example.googlemapsapp.ui.composables.current_places

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.googlemapsapp.classes.Place
import com.example.googlemapsapp.ui.composables.ConstructedHeader
import com.example.googlemapsapp.view_models.CurrentPlacesUiState
import com.example.googlemapsapp.view_models.CurrentPlacesViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CurrentPlacesScreen(
    viewModel: CurrentPlacesViewModel,
    onShowOnMapButtonClick: (Place) -> Unit,
    onViewDetailsButtonClick: (Place) -> Unit,
) {
    val currentPlacesUiState = viewModel.currentPlacesUiState

    LaunchedEffect(Unit) {
        if(currentPlacesUiState is CurrentPlacesUiState.Error) {
            viewModel.refresh()
        }
    }

    Column {
        ConstructedHeader(text = "Nearby Places")

        when (currentPlacesUiState) {
            is CurrentPlacesUiState.Success -> CurrentPlacesSuccessScreen(
                viewModel = viewModel,
                onShowOnMapButtonClick = onShowOnMapButtonClick,
                onViewDetailsButtonClick = onViewDetailsButtonClick
            )
            is CurrentPlacesUiState.Loading -> CurrentPlacesLoadingScreen()
            is CurrentPlacesUiState.Error -> CurrentPlacesErrorScreen(
                statusCode = currentPlacesUiState.statusCode,
                onRetryClick = {
                    viewModel.refresh()
                }
            )
        }
   }
}
