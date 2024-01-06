package com.example.googlemapsapp.ui.composables.map

import androidx.compose.runtime.Composable
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun PlaceMarker(
    name: String,
    latitude: Double,
    longitude: Double,
    isColorDiffer: Boolean
) {
    val color = if(isColorDiffer) BitmapDescriptorFactory.HUE_RED else BitmapDescriptorFactory.HUE_YELLOW
    Marker(
        state = rememberMarkerState(position = LatLng(latitude, longitude)),
        title = name,
        icon = BitmapDescriptorFactory.defaultMarker(color)
    )
}