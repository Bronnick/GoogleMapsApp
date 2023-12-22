package com.example.googlemapsapp.ui.composables.current_places

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.googlemapsapp.classes.CurrentPlace
import com.example.googlemapsapp.ui.composables.current_places.details.PlaceOverview
import androidx.compose.foundation.lazy.items
import com.example.googlemapsapp.view_models.CurrentPlacesViewModel

@Composable
fun CurrentPlacesSuccessScreen(
    currentPlaces: List<CurrentPlace>,
    viewModel: CurrentPlacesViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ){
        items(currentPlaces){ place ->
            PlaceOverview(
                place = place,
                onFavoritesIconClick = {
                    viewModel.changePlaceFavoriteStatus(place)
                },
                viewModel = viewModel
            )
        }
    }
    /*Column(
        modifier = Modifier.fillMaxSize()
    ) {
        for(place in currentPlaces){
            PlaceOverview(
                place = place,
                onFavoritesIconClick = {
                    viewModel.changePlaceFavoriteStatus(place)
                    viewModel.test += "s"
                },
                viewModel = viewModel
            )
        }
    }*/
}