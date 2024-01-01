package com.example.googlemapsapp.ui.composables.favorites

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.googlemapsapp.classes.Place
import androidx.compose.runtime.*
import com.example.googlemapsapp.ui.composables.place_info.PlaceOverview
import com.example.googlemapsapp.view_models.FavouritePlacesViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavouritePlacesScreen(
    viewModel: FavouritePlacesViewModel,
    onShowOnMapButtonClick: (Place) -> Unit,
    onViewDetailsButtonClick: (Place) -> Unit,
) {
    val placeList by viewModel.favoritePlaces.collectAsState(initial = emptyList())


    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(placeList) { place ->
            SwipeToDismiss(
                state = rememberDismissState(
                    initialValue = DismissValue.Default,
                    confirmStateChange = {
                        if(it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart){
                            viewModel.deletePlace(place)
                        }
                        false
                    }
                ),
                background = {}) {
                    PlaceOverview(
                        place = place,
                        onShowOnMapButtonClick = onShowOnMapButtonClick,
                        onViewDetailsButtonClick = onViewDetailsButtonClick,
                        isFavoriteScreen = false
                    )
            }
        }
    }
}