package com.example.googlemapsapp.di

import android.content.Context
import androidx.datastore.core.*
import androidx.datastore.preferences.core.Preferences
import com.example.googlemapsapp.utils.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatastoreModule {

    @Provides
    fun provideDatastore(@ApplicationContext applicationContext: Context): DataStore<Preferences> {
        return applicationContext.dataStore
    }
}
