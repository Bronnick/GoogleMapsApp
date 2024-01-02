package com.example.googlemapsapp.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlemapsapp.classes.Place
import com.example.googlemapsapp.repositories.AppSettingsRepository
import com.example.googlemapsapp.repositories.PlacesRepository
import com.example.googlemapsapp.utils.mapTypeParam
import com.example.googlemapsapp.utils.trafficParam
import com.google.maps.android.compose.MapType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
    private val placesRepository: PlacesRepository
) : ViewModel() {
    val favoritePlaces: Flow<List<Place>> =
        placesRepository.getFavoritePlaces()

    private val _mapType = MutableLiveData(MapType.NORMAL)
    val mapType
        get() = _mapType

    private val _isTrafficEnabled = MutableLiveData(false)
    val isTrafficEnabled
        get() = _isTrafficEnabled

    init {
        viewModelScope.launch {
            setMapType(appSettingsRepository.getParameterByKey(mapTypeParam) as? String ?: "Normal")
            setTraffic(appSettingsRepository.getParameterByKey(trafficParam) as? Boolean ?: false)
        }
    }

    fun setMapType(value: String) {
        _mapType.value = when(value) {
            "Normal" -> MapType.NORMAL
            "Hybrid" -> MapType.HYBRID
            "Terrain" -> MapType.TERRAIN
            else -> MapType.NORMAL
        }
    }

    fun setTraffic(value: Boolean) {
        _isTrafficEnabled.value = value
    }
}