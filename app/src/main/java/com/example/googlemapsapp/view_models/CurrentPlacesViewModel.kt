package com.example.googlemapsapp.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class CurrentPlacesViewModel @Inject constructor(
    @ApplicationContext applicationContext: Context
) : ViewModel() {

}