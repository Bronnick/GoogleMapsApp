package com.example.googlemapsapp.di

import com.example.googlemapsapp.places_api.CurrentPlaceService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

//    @Inject lateinit var currentPlaceService: CurrentPlaceService
//
//    @Singleton
//    @Provides
//    fun provideCurrentPlaceService(): CurrentPlaceService{
//        return currentPlaceService
//    }
}