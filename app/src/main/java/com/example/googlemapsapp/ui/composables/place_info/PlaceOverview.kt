package com.example.googlemapsapp.ui.composables.place_info

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.googlemapsapp.R
import com.example.googlemapsapp.classes.Place
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.imageResource

@Composable
fun PlaceOverview(
    place: Place,
    onFavoritesIconClick: () -> Unit = {},
    onShowOnMapButtonClick: (Place) -> Unit,
    onViewDetailsButtonClick: (Place) -> Unit,
    isFavoriteScreen: Boolean
) {
    var favoriteState by remember {
        mutableStateOf(place.isFavorite)
    }

    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {

            if (place.photoRef?.trim()?.isNotEmpty() == true) {
                AsyncImage(
                    /*model = place.photos[0].attributions.substringAfter('"')
                    .substringBefore('"'),*/
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    model = stringResource(
                        id = R.string.photo_ref,
                        place.photoRef ?: ""
                    ),
                    contentDescription = null
                )
            } else {
                Image(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .size(150.dp),
                    painter = BitmapPainter(ImageBitmap.imageResource(id = R.drawable.image_not_found)),
                    contentDescription = null
                )
            }

            Text(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 16.dp),
                text = place.name ?: "",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
        ) {
            if (isFavoriteScreen) {
                IconButton(
                    onClick = {
                        favoriteState = !favoriteState
                        onFavoritesIconClick()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite /*if(place.isFavorite) Icons.Default.Favorite
                                else Icons.Filled.Favorite*/,
                        tint = if (favoriteState) Color.Red else Color.Unspecified,
                        contentDescription = null
                    )
                }
            }

            Button(
                onClick = {
                    onShowOnMapButtonClick(place)
                }
            ) {
                Text(
                    text = "Show on map"
                )
            }

            Spacer(Modifier.size(16.dp))

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                onClick = {
                    onViewDetailsButtonClick(place)
                }
            ) {
                Text(
                    text = "View details"
                )
            }

        }
    }
}
