package com.example.googlemapsapp.ui.composables.current_places

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.googlemapsapp.classes.Place
import androidx.compose.runtime.*
import com.example.googlemapsapp.ui.composables.place_info.PlaceOverview
import com.example.googlemapsapp.view_models.CurrentPlacesUiState
import com.example.googlemapsapp.view_models.CurrentPlacesViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun CurrentPlacesSuccessScreen(
    viewModel: CurrentPlacesViewModel,
    onShowOnMapButtonClick: (Place) -> Unit,
    onViewDetailsButtonClick: (Place) -> Unit,
) {
    val placeList by (viewModel.currentPlacesUiState as CurrentPlacesUiState.Success).places.collectAsState(
        initial = emptyList()
    )

    Column {
        Button(
            onClick = {
                viewModel.refresh()
            }
        ) {
            Text(
                text = "Refresh all"
            )
        }

        Button(
            onClick = {
                viewModel.refreshList()
            }
        ) {
            Text(
                text = "Refresh"
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(placeList) { place ->
                PlaceOverview(
                    place = place,
                    onFavoritesIconClick = {
                        viewModel.changePlaceFavoriteStatus(place)
                    },
                    onShowOnMapButtonClick = onShowOnMapButtonClick,
                    onViewDetailsButtonClick = onViewDetailsButtonClick,
                    isFavoriteScreen = true
                )
            }
        }
    }
}