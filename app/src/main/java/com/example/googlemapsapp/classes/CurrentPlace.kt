package com.example.googlemapsapp.classes

import com.google.android.libraries.places.api.model.PhotoMetadata

data class CurrentPlace (
    val name: String,
    val likelihood: Double,
    val photos: List<PhotoMetadata>
)
