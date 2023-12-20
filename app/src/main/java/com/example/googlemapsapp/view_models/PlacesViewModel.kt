package com.example.googlemapsapp.view_models

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient

class PlacesViewModel(private val applicationContext: Context) : ViewModel() {
    private val placesClient: PlacesClient
    private val placeFields: List<Place.Field> = listOf(Place.Field.NAME)

    init {
        Places.initializeWithNewPlacesApiEnabled(
            applicationContext,
            "AIzaSyBlrwhE1xSDXu-sH-BCHpxRNLZy8iFKlek"
        )

        // Create a new PlacesClient instance
        placesClient = Places.createClient(applicationContext)
    }

    fun getNearbyPlaces() {
        val request: FindCurrentPlaceRequest = FindCurrentPlaceRequest.newInstance(placeFields)

// Call findCurrentPlace and handle the response (first check that the user has granted permission).
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {

            val placeResponse = placesClient.findCurrentPlace(request)
            placeResponse.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val response = task.result
                    for (placeLikelihood: PlaceLikelihood in response?.placeLikelihoods
                        ?: emptyList()) {
                        Log.d(
                            "myLogs",
                            "Place '${placeLikelihood.place.name}' has likelihood: ${placeLikelihood.likelihood}"
                        )
                    }
                } else {
                    val exception = task.exception
                    if (exception is ApiException) {
                        Log.e("myLogs", "Place not found: ${exception.statusCode}")
                    }
                }
            }
        }
    }
}

