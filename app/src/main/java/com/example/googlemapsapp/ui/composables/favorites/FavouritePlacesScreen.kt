package com.example.googlemapsapp.ui.composables.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.googlemapsapp.classes.Place
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.googlemapsapp.ui.composables.place_info.PlaceOverview
import com.example.googlemapsapp.view_models.FavouritePlacesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritePlacesScreen(
    viewModel: FavouritePlacesViewModel,
    onShowOnMapButtonClick: (Place) -> Unit,
    onViewDetailsButtonClick: (Place) -> Unit,
    onDeleteFavoritePlace: (Place) -> Unit
) {
    val placeList by viewModel.favoritePlaces.collectAsState(initial = emptyList())


    LazyColumn(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        items(placeList) { place ->
            /*val dismissState = rememberDismissState()

            if(dismissState.isDismissed(DismissDirection.EndToStart) || dismissState.isDismissed(DismissDirection.StartToEnd)){
                onDeleteFavoritePlace(place)
            }*/

            SwipeToDismiss(
                state = rememberDismissState(
                    initialValue = DismissValue.Default,
                    confirmValueChange = {
                        if(it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart){
                            onDeleteFavoritePlace(place)
                        }
                        false
                    }
                ),
                background = {},
                dismissContent = {
                    PlaceOverview(
                        place = place,
                        onShowOnMapButtonClick = onShowOnMapButtonClick,
                        onViewDetailsButtonClick = onViewDetailsButtonClick,
                        isFavoriteScreen = false
                    )
                })
        }
    }
}