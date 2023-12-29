package com.example.googlemapsapp.ui.composables.place_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.googlemapsapp.R

@Composable
fun DetailsScreen(
    name: String?,
    photoRef: String,
    address: String?,
    rating: Double?
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Row {
                if(photoRef.trim().isNotEmpty()) {
                    AsyncImage(
                        model = stringResource(
                            id = R.string.photo_ref,
                            photoRef ?: ""
                        ),
                        contentDescription = null
                    )
                } else{
                    Image(
                        modifier = Modifier.size(150.dp),
                        painter = BitmapPainter(ImageBitmap.imageResource(id = R.drawable.image_not_found)),
                        contentDescription = null
                    )
                }
                Text(
                    text = "Name: $name"
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
}