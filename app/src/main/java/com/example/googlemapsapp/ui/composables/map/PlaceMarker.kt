package com.example.googlemapsapp.ui.composables.map

import androidx.compose.runtime.Composable
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun PlaceMarker(
    latitude: Double,
    longitude: Double,
) {
    Marker(
        state = rememberMarkerState(position = LatLng(latitude, longitude)),
        title = "Marker",
        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
    )
}