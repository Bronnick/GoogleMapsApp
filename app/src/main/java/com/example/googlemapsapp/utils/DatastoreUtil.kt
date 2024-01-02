package com.example.googlemapsapp.utils

import android.content.Context
import androidx.compose.runtime.currentRecomposeScope
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val settingTextParam = stringPreferencesKey("setting_text")
val maxCurrentPlacesNumberParam = intPreferencesKey("max_current_places")
val mapTypeParam = stringPreferencesKey("map_type_setting")
val trafficParam = booleanPreferencesKey("traffic_setting")