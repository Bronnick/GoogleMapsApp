package com.example.googlemapsapp.classes

import android.graphics.Bitmap
import com.google.android.libraries.places.api.model.PhotoMetadata

data class CurrentPlace (
    val name: String,
    val likelihood: Double,
    val photo: String?,
    var isFavorite: Boolean
)
