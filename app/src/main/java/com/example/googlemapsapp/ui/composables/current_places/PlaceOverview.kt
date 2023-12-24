package com.example.googlemapsapp.ui.composables.current_places

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
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
    viewModel: CurrentPlacesViewModel,
    place: Place,
    onFavoritesIconClick: () -> Unit,
    onShowOnMapButtonClick: (Place) -> Unit
) {
    var favoriteState by remember {
        mutableStateOf(place.isFavorite)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ){
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

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
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

                Button(
                    onClick = {
                        onShowOnMapButtonClick(place)
                    }
                ) {
                    Text(
                        text = "Show on map"
                    )
                }
            }
        }
    }
}