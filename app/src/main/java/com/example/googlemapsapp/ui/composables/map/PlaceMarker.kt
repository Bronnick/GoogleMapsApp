package com.example.googlemapsapp.ui.composables.map

import androidx.compose.runtime.Composable
import com.example.googlemapsapp.classes.Place
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun PlaceMarker(
    place: Place,
    latitude: Double,
    longitude: Double,
) {
    Marker(
        state = rememberMarkerState(position = LatLng(latitude, longitude)),
        title = place.name,
        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
    )
}