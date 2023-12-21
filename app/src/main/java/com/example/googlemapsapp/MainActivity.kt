package com.example.googlemapsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.googlemapsapp.places_api.CurrentPlaceService
import com.example.googlemapsapp.repositories.CurrentPlaceRepository
import com.example.googlemapsapp.ui.composables.MainScreen
import com.example.googlemapsapp.ui.theme.GoogleMapsAppTheme
import com.example.googlemapsapp.view_models.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var currentPlaceRepository: CurrentPlaceRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settingsViewModel: SettingsViewModel by viewModels()

//        placesService1.sendCurrentLocationRequest()
//        placesService2.sendCurrentLocationRequest()


        //currentPlaceRepository.getCurrentPlacesLikelihood()

        currentPlaceRepository.getCurrentPlacesLikelihood()

        setContent {
            GoogleMapsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(settingsViewModel)
                }
            }
        }
    }
}

