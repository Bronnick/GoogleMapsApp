package com.example.googlemapsapp.ui.composables.current_places.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.googlemapsapp.classes.CurrentPlace
import com.google.android.libraries.places.api.model.PlaceLikelihood

@Composable
fun PlaceOverview(
    place: CurrentPlace
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ){
        Column {
            Row {
                /*AsyncImage(
                    model =  place.photos[0].attributions.substringAfter('"').substringBefore('"'),
                    contentDescription = null
                )*/
                Text(
                    text = place.name,
                    textAlign = TextAlign.End
                )
            }
            Text(
                text = place.likelihood.toString(),
                textAlign = TextAlign.End
            )
        }
    }
}