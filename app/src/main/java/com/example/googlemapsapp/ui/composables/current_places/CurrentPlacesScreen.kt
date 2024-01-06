package com.example.googlemapsapp.ui.composables.current_places

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.googlemapsapp.classes.Place
import com.example.googlemapsapp.ui.composables.ConstructedHeader
import com.example.googlemapsapp.view_models.CurrentPlacesUiState
import com.example.googlemapsapp.view_models.CurrentPlacesViewModel

@Composable
fun CurrentPlacesScreen(
    viewModel: CurrentPlacesViewModel,
    onShowOnMapButtonClick: (Place) -> Unit,
    onViewDetailsButtonClick: (Place) -> Unit,
) {
    Column {
        ConstructedHeader(text = "Nearby Places")

        when (val currentPlacesUiState = viewModel.currentPlacesUiState) {
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
