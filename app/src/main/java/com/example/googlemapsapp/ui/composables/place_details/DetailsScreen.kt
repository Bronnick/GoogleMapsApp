package com.example.googlemapsapp.ui.composables.place_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.googlemapsapp.R
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt

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
            PlaceDetails(
                name = name,
                photoRef = photoRef,
                address = address,
                rating = rating
            )
        }
    }
}

@Composable
fun PlaceDetails(
    name: String?,
    photoRef: String,
    address: String?,
    rating: Double?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {
        Row {
            if (photoRef.trim().isNotEmpty()) {
                AsyncImage(
                    modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                    model = stringResource(
                        id = R.string.photo_ref,
                        photoRef ?: ""
                    ),
                    contentDescription = null
                )
            } else {
                Image(
                    modifier = Modifier.size(150.dp),
                    painter = BitmapPainter(ImageBitmap.imageResource(id = R.drawable.image_not_found)),
                    contentDescription = null
                )
            }
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "Name: $name"
            )
        }

        Row(
            modifier = Modifier.padding(top = 8.dp),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_location_on_24),
                contentDescription = null
            )
            Text(
                text = address ?: "unknown"
            )
        }

        Row(
            modifier = Modifier.padding(top = 8.dp),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_star_rate_24),
                contentDescription = null
            )


            Text(
                text = rating?.toBigDecimal()?.setScale(1, RoundingMode.CEILING)?.toDouble()
                    .toString()
            )
        }
    }
}
