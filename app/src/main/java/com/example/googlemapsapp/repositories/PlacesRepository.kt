package com.example.googlemapsapp.repositories

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.googlemapsapp.classes.Place
import com.example.googlemapsapp.database.PlaceDao
import com.example.googlemapsapp.places_api.CurrentPlaceService
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PlacesRepository @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val currentPlaceService: CurrentPlaceService,
    private val placeDao: PlaceDao
) {
    fun getCurrentPlaces(): Task<FindCurrentPlaceResponse> {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            return currentPlaceService.getCurrentLocationRequest()
        } else{
            throw Exception()
        }
    }

    fun getFavoritePlaces() = placeDao.getPlaces()

    suspend fun insertPlace(place: Place) {
        placeDao.insertPlace(place)
    }

}