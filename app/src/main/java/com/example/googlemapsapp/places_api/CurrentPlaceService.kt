package com.example.googlemapsapp.places_api

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentPlaceService @Inject constructor(
    @ApplicationContext private val applicationContext: Context
){
    private val placeFields: List<Place.Field> = listOf(Place.Field.NAME)

    var placesClient: PlacesClient

    init{
        Places.initializeWithNewPlacesApiEnabled(
            applicationContext,
            "AIzaSyBlrwhE1xSDXu-sH-BCHpxRNLZy8iFKlek"
        )
        Log.d("myLogs", "placeService init")
        // Create a new PlacesClient instance
        //return Places.createClient(context)
        placesClient = Places.createClient(applicationContext)
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    fun getCurrentLocationRequest(): Task<FindCurrentPlaceResponse> {

        // Use fields to define the data types to return.
        val placeFields: List<Place.Field> = listOf(Place.Field.NAME)

// Use tuilder to create a FindCurrentPlaceRequest.
        val request: FindCurrentPlaceRequest = FindCurrentPlaceRequest.newInstance(placeFields)

        Log.d("myLogs", "send request")
        return placesClient.findCurrentPlace(request)
        /*val placeResponse = placesClient.findCurrentPlace(request)
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
        }*/
        }


}