package com.example.googlemapsapp.ui.composables.place_info

import androidx.compose.foundation.layout.*
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
import com.example.googlemapsapp.view_models.CurrentPlacesViewModel

val photoExample = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=AWU5eFhOeU-PGDK97Lec9K0e3GYZ2HsBl_u8V_w2xa-SUqyP9O-Urlzr53U_VP91seElwit7c_KYZM9mAgtDlsO-qXB7ec6IRgPzOrz38mgypMHi23RVqImcI_3d4HitUWSkYINMNaBDUivYzY93xr2m3ky96s8K2WkWkcZeBGiAiXO1C8kw&key=AIzaSyBlrwhE1xSDXu-sH-BCHpxRNLZy8iFKlek"

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
            containerColor = MaterialTheme.colorScheme.surface
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

            Text(
                modifier = Modifier.padding(start = 8.dp, top = 16.dp),
                text = place.name ?: "",
                textAlign = TextAlign.End,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
        ) {
            if(isFavoriteScreen) {
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
