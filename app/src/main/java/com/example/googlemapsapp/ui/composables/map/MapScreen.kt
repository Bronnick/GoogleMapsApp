package com.example.googlemapsapp.ui.composables.map

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    latitude: Float,
    longitude: Float
) {
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
            .padding(16.dp)
    ) {
        Text(
            text = "Welcome to the MapsApp!",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))

        PermissionsRequired(
            multiplePermissionsState = multiplePermissionState,
            permissionsNotGrantedContent = { /* ... */ },
            permissionsNotAvailableContent = { /* ... */ }
        ) {
            GoogleMap(
                cameraPositionState = cameraPositionState,
                modifier = Modifier.weight(1f),
                properties = MapProperties(isMyLocationEnabled = true),
                uiSettings = MapUiSettings(compassEnabled = true)
            ) {
                PlaceMarker(
                    latitude = latitude.toDouble(),
                    longitude = longitude.toDouble(),
                )
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
