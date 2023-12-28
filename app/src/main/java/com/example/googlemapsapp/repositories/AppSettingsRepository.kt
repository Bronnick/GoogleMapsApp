package com.example.googlemapsapp.repositories

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun getParameterByKey(
        key: Preferences.Key<*>
    ) = dataStore.data.map {preferences ->
        preferences[key]
    }
        .first()

    suspend fun <T> updateSettingText(
        preferenceKey: Preferences.Key<T>,
        value: T
    ) {
        dataStore.edit { preferences ->
            preferences[preferenceKey] = value
        }
        Log.d("myLogs", "updated value $preferenceKey")
    }
}