package com.example.googlemapsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.example.googlemapsapp.ui.composables.MainScreen
import com.example.googlemapsapp.ui.theme.GoogleMapsAppTheme
import com.example.googlemapsapp.view_models.CurrentPlacesViewModel
import com.example.googlemapsapp.view_models.FavouritePlacesViewModel
import com.example.googlemapsapp.view_models.MapViewModel
import com.example.googlemapsapp.view_models.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mapViewModel: MapViewModel by viewModels()
        val currentPlacesViewModel: CurrentPlacesViewModel by viewModels()
        val favouritePlacesViewModel: FavouritePlacesViewModel by viewModels()
        val settingsViewModel: SettingsViewModel by viewModels()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            GoogleMapsAppTheme {
                MainScreen(
                    mapViewModel = mapViewModel,
                    currentPlacesViewModel = currentPlacesViewModel,
                    favouritePlacesViewModel = favouritePlacesViewModel,
                    settingsViewModel = settingsViewModel
                )
            }
        }

    }
}

