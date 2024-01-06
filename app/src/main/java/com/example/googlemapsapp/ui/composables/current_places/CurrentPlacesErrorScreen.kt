package com.example.googlemapsapp.ui.composables.current_places

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CurrentPlacesErrorScreen(
    statusCode: Int,
    onRetryClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Something went wrong:(\nStatus code is $statusCode",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            IconButton(
                onClick = onRetryClick
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = null
                )
            }
        }
    }
}