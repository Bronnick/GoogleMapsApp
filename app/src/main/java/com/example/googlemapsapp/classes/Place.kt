package com.example.googlemapsapp.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "places")
data class Place (

    @PrimaryKey
    @ColumnInfo(name = "id")
    val placeId: String,

    val name: String,

    val likelihood: Double,

    val latitude: Double,

    val longitude: Double,

    val photoRef: String?,

    val address: String?,

    val rating: Double?,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Place

        if (placeId != other.placeId) return false
        if (name != other.name) return false
        if (likelihood != other.likelihood) return false
        if (photoRef != other.photoRef) return false

        return true
    }

    override fun hashCode(): Int {
        var result = placeId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + likelihood.hashCode()
        result = 31 * result + (photoRef?.hashCode() ?: 0)
        return result
    }
}
