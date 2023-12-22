package com.example.googlemapsapp.ui.composables.current_places

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.googlemapsapp.classes.CurrentPlace
import com.example.googlemapsapp.ui.composables.current_places.details.PlaceOverview
import androidx.compose.foundation.lazy.items

@Composable
fun CurrentPlacesSuccessScreen(
    currentPlaces: List<CurrentPlace>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ){
        items(currentPlaces){ place ->
            PlaceOverview(
                place = place
            )
        }
    }
}