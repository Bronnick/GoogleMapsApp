package com.example.googlemapsapp.repositories

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.googlemapsapp.places_api.CurrentPlaceService
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.PlaceLikelihood
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CurrentPlaceRepository @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val currentPlaceService: CurrentPlaceService
) {
    fun getCurrentPlacesLikelihood(){
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            val placeResponse = currentPlaceService.getCurrentLocationRequest()
            placeResponse.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val response = task.result
                    for (placeLikelihood: PlaceLikelihood in response?.placeLikelihoods
                        ?: emptyList()) {
                        Log.i(
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