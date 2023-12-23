package com.example.googlemapsapp.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places")
data class Place (

    @PrimaryKey
    @ColumnInfo(name = "id")
    val placeId: String,

    val name: String,

    val likelihood: Double,

    val photo: String?,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean
)
