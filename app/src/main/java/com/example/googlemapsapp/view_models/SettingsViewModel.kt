package com.example.googlemapsapp.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlemapsapp.repositories.AppSettingsRepository
import com.example.googlemapsapp.utils.mapTypeParam
import com.example.googlemapsapp.utils.maxCurrentPlacesNumberParam
import com.example.googlemapsapp.utils.settingTextParam
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

    var settingText by mutableStateOf("Not yet implemented :(")

    var maxCurrentPlacesNumber by mutableStateOf(10)
        private set

    private val _showMapTypeDropdownMenu = MutableLiveData(false)
    val showMapTypeDropdownMenu
        get() = _showMapTypeDropdownMenu

    private val _selectedMapTypeValue = MutableLiveData("Normal")
        val selectedMapTypeValue
            get() = _selectedMapTypeValue

    init{
        viewModelScope.launch {
            settingText = settingsRepository.getParameterByKey(settingTextParam) as? String ?: ""
            maxCurrentPlacesNumber = settingsRepository.getParameterByKey(
                maxCurrentPlacesNumberParam) as? Int ?: 10
            selectedMapTypeValue.value = settingsRepository.getParameterByKey(mapTypeParam)
                    as? String ?: "Normal"
        }
    }

    fun setMapTypeDropdownMenuVisibility(value: Boolean) {
        _showMapTypeDropdownMenu.value = value
    }

    fun <T> updateSettings(key: Preferences.Key<T>, newValue: T) {
        when(key) {
            settingTextParam -> settingText = newValue as? String ?: ""
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