package com.example.googlemapsapp.ui.composables.map

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.googlemapsapp.view_models.MapViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.PointOfInterest
import com.google.maps.android.compose.*


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    viewModel: MapViewModel,
    latitude: Float,
    longitude: Float
) {
    val mapType = viewModel.mapType.observeAsState().value
    val isTrafficEnabled = viewModel.isTrafficEnabled.observeAsState().value

    val displayedPlaces = viewModel.favoritePlaces.collectAsState(initial = emptyList()).value

    val multiplePermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(latitude.toDouble(), longitude.toDouble()), 17f)
    }

    LaunchedEffect(Unit) {
        multiplePermissionState.launchMultiplePermissionRequest()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(8.dp)
    ) {
        PermissionsRequired(
            multiplePermissionsState = multiplePermissionState,
            permissionsNotGrantedContent = { /* ... */ },
            permissionsNotAvailableContent = { /* ... */ }
        ) {
            GoogleMap(
                cameraPositionState = cameraPositionState,
                modifier = Modifier.weight(1f),
                properties = MapProperties(
                    mapType = mapType ?: MapType.NORMAL,
                    isMyLocationEnabled = true,
                    isBuildingEnabled = true,
                    isTrafficEnabled = isTrafficEnabled ?: false
                ),
                uiSettings = MapUiSettings(
                    compassEnabled = true,
                    myLocationButtonEnabled = true
                )
            ) {
                displayedPlaces.forEach { place ->
                    PlaceMarker(
                        place = place,
                        latitude = place.latitude,
                        longitude = place.longitude,
                        isColorDiffer = place.latitude.toFloat() == latitude &&
                                place.longitude.toFloat() == longitude
                    )
                }
                Polyline(
                    points = listOf(
                        LatLng(44.811058, 20.4617586),
                        LatLng(44.811058, 20.4627586),
                        LatLng(44.810058, 20.4627586),
                        LatLng(44.809058, 20.4627586),
                        LatLng(44.809058, 20.4617586)
                    )
                )
            }
        }
    }


}
