package com.example.googlemapsapp.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlemapsapp.repositories.AppSettingsRepository
import com.example.googlemapsapp.utils.mapTypeParam
import com.example.googlemapsapp.utils.maxCurrentPlacesNumberParam
import com.example.googlemapsapp.utils.settingTextParam
import com.example.googlemapsapp.utils.trafficParam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: AppSettingsRepository
) : ViewModel() {

    /*@OptIn(SavedStateHandleSaveableApi::class)
    var settingText by savedStateHandle.saveable(stateSaver = TextFieldValue.Saver){
        mutableStateOf(TextFieldValue("Not yet implemented :("))
    }
        private set*/

    var maxCurrentPlacesNumber by mutableStateOf(10)
        private set

    private val _showMapTypeDropdownMenu = MutableLiveData(false)
    val showMapTypeDropdownMenu: LiveData<Boolean>
        get() = _showMapTypeDropdownMenu

    private val _selectedMapTypeValue = MutableLiveData("Normal")
    val selectedMapTypeValue
        get() = _selectedMapTypeValue

    private val _isTrafficEnabled = MutableLiveData(false)
    val isTrafficEnabled: LiveData<Boolean>
        get() = _isTrafficEnabled

    init{
        viewModelScope.launch {
            maxCurrentPlacesNumber = settingsRepository.getParameterByKey(
                maxCurrentPlacesNumberParam) as? Int ?: 10
            _selectedMapTypeValue.value = settingsRepository.getParameterByKey(mapTypeParam)
                    as? String ?: "Normal"
            _isTrafficEnabled.value = settingsRepository.getParameterByKey(trafficParam) as? Boolean
                ?: false
        }
    }

    fun setMapTypeDropdownMenuVisibility(value: Boolean) {
        _showMapTypeDropdownMenu.value = value
    }

    fun setTraffic(value: Boolean) {
        _isTrafficEnabled.value = value
    }

    fun <T> updateSettings(key: Preferences.Key<T>, newValue: T) {
        when(key) {
            maxCurrentPlacesNumberParam -> maxCurrentPlacesNumber = newValue as? Int ?: 10
            mapTypeParam -> selectedMapTypeValue.value = newValue as? String ?: "Normal"
        }
        viewModelScope.launch {
            settingsRepository.updateSettings(
                key,
                newValue
            )
        }
    }

    companion object{
        const val SETTINGS_TEXT_KEY = "settings_text_key"
    }
}