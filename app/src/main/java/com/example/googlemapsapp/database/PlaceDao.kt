package com.example.googlemapsapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.googlemapsapp.classes.Place
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {

    @Query("SELECT * FROM places")
    fun getPlaces(): Flow<List<Place>>

    @Query("SELECT * FROM places WHERE id = :placeId")
    fun getPlaceById(placeId: String): Place?

    @Insert
    suspend fun insertPlace(place: Place)

    @Delete
    suspend fun deletePlace(place: Place)
}