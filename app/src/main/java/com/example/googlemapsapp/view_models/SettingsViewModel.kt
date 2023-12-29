package com.example.googlemapsapp.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.example.googlemapsapp.repositories.AppSettingsRepository
import com.example.googlemapsapp.utils.maxCurrentPlacesNumberParam
import com.example.googlemapsapp.utils.settingTextParam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
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

    init{
        viewModelScope.launch {
            settingText = settingsRepository.getParameterByKey(settingTextParam) as? String ?: ""
            maxCurrentPlacesNumber = settingsRepository.getParameterByKey(
                maxCurrentPlacesNumberParam) as? Int ?: 10
        }
    }

    fun <T> updateSettings(key: Preferences.Key<T>, newValue: T){
        viewModelScope.launch {
            settingsRepository.updateSettings(
                key,
                newValue
            )
            when(key){
                settingTextParam -> settingText = newValue as? String ?: ""
                maxCurrentPlacesNumberParam -> maxCurrentPlacesNumber = newValue as? Int ?: 10
            }
        }
    }

    companion object{
        const val SETTINGS_TEXT_KEY = "settings_text_key"
    }
}