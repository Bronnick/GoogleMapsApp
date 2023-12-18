package com.example.googlemapsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.googlemapsapp.ui.composables.MainScreen
import com.example.googlemapsapp.ui.theme.GoogleMapsAppTheme
import com.google.android.libraries.places.api.Places

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            // Initialize the SDK
            Places.initializeWithNewPlacesApiEnabled(applicationContext, "AIzaSyBlrwhE1xSDXu-sH-BCHpxRNLZy8iFKlek")

            // Create a new PlacesClient instance
            val placesClient = Places.createClient(this)
            Log.d("myLogs", "successfully initialised")
        } catch(e: Exception){
            Log.d("myLogs", e.message ?: "")
        }

        setContent {
            GoogleMapsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoogleMapsAppTheme {
        Greeting("Android")
    }
}