package com.example.googlemapsapp.ui.composables.current_places.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.libraries.places.api.model.PlaceLikelihood

@Composable
fun PlaceOverview(
    name: String,
    likelihood: Double
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(100.dp)
    ){
        Column() {
            Text(
                text = name,
                textAlign = TextAlign.Start
            )
            Text(
                text = likelihood.toString(),
                textAlign = TextAlign.End
            )
        }
    }
}