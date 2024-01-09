package com.example.googlemapsapp.places_api

import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentPlaceService @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) {

    private var placesClient: PlacesClient

    init {
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
        val placeFields: List<Place.Field> = listOf(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.PHOTO_METADATAS,
            Place.Field.LAT_LNG,
            Place.Field.ADDRESS,
            Place.Field.RATING
        )

        val request: FindCurrentPlaceRequest = FindCurrentPlaceRequest.newInstance(placeFields)

        Log.d("myLogs", "send request")
        return placesClient.findCurrentPlace(request)

    }
}