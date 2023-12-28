package com.example.googlemapsapp.ui.composables.place_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.googlemapsapp.R

@Composable
fun DetailsScreen(
    name: String,
    photoRef: String,
    address: String,
    rating: Double
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row {
            AsyncImage(
                model = stringResource(
                    id = R.string.photo_ref,
                    photoRef
                ),
                contentDescription = null
            )
            Text(
               text = name
            )
        }
        Text(
            text = "Address: $address"
        )
        Text(
            text = "Rating: $rating"
        )
    }
}