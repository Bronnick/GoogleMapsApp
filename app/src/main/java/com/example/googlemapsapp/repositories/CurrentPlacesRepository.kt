package com.example.googlemapsapp.repositories

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.googlemapsapp.places_api.CurrentPlaceService
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.google.android.libraries.places.api.net.FetchPhotoResponse
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CurrentPlacesRepository @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val currentPlaceService: CurrentPlaceService
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

    fun getPhoto(photoMetadata: PhotoMetadata?): Task<FetchPhotoResponse>?{
        return run {
            if(photoMetadata != null){
                currentPlaceService.getCurrentLocationPhoto(photoMetadata)
            }
            null
        }
        //with(currentPlaceService.getCurrentLocationPhoto(photoMetadata)
    }
}