package com.example.googlemapsapp.ui.composables.favorites.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.googlemapsapp.R
import com.example.googlemapsapp.classes.Place
import com.example.googlemapsapp.view_models.FavouritePlacesViewModel

@Composable
fun PlaceOverview(
    place: Place,
    viewModel: FavouritePlacesViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                AsyncImage(
                    /*model = place.photos[0].attributions.substringAfter('"')
                        .substringBefore('"'),*/
                    modifier = Modifier.size(150.dp),
                    model = stringResource(
                        id = R.string.photo_ref,
                        place.photoRef ?: ""
                    ),
                    contentDescription = null
                )

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.padding(start = 8.dp, top = 16.dp),
                        text = place.isFavorite.toString(),
                        textAlign = TextAlign.End,
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp, top = 16.dp),
                        text = "${place.latitude}, ${place.longitude}",
                        textAlign = TextAlign.End,
                    )
                }
            }
            Text(
                text = place.placeId,
                textAlign = TextAlign.End
            )
        }
    }
}