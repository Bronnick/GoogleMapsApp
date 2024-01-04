package com.example.googlemapsapp.ui.composables.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.googlemapsapp.utils.maxCurrentPlacesNumberParam
import com.example.googlemapsapp.utils.settingTextParam
import com.example.googlemapsapp.view_models.SettingsViewModel
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import com.example.googlemapsapp.utils.mapTypeParam
import com.google.maps.android.compose.MapType

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onMapTypeChanged: (String) -> Unit,
    onTrafficChange: (Boolean) -> Unit
) {
    var maxCurrentPlacesNumber by remember {
        mutableStateOf(viewModel.maxCurrentPlacesNumber.toString())
    }

    val showMapTypeDropdownMenu = viewModel.showMapTypeDropdownMenu.observeAsState().value
    val selectedDropdownMenuValue = viewModel.selectedMapTypeValue.observeAsState().value
    val isTrafficEnabled = viewModel.isTrafficEnabled.observeAsState().value

    Column {
        Text(
            modifier = Modifier.clickable {
                viewModel.updateSettings(settingTextParam, viewModel.settingText + "1")
            },
            text = viewModel.settingText
        )

        MaxCurrentPlacesSetting(
            maxCurrentPlacesNumber = maxCurrentPlacesNumber,
            onValueChange = {
                maxCurrentPlacesNumber = try {
                    viewModel.updateSettings(maxCurrentPlacesNumberParam, it.toInt())
                    it
                } catch(e: NumberFormatException) {
                    it
                }
            }
        )
        MapTypeSetting(
            expanded = showMapTypeDropdownMenu ?: false,
            selectedValue = selectedDropdownMenuValue ?: "Normal",
            onDropdownMenuExpand = {
                viewModel.setMapTypeDropdownMenuVisibility(true)
            },
            onDropdownMenuDismiss = {
                viewModel.setMapTypeDropdownMenuVisibility(false)
            },
            onDropdownMenuItemSelect = onMapTypeChanged
        )
        TrafficSetting(
            isTrafficEnabled = isTrafficEnabled!!,
            onTrafficChange = onTrafficChange
        )
    }
}

@Composable
fun MaxCurrentPlacesSetting(
    maxCurrentPlacesNumber: String,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                val borderSize = 1.dp
                drawLine(
                    color = Color.White,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderSize.toPx(),
                    pathEffect = PathEffect.cornerPathEffect(5.0f)
                )
            }
            .padding(top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Max current places: "
        )
        OutlinedTextField(
            value = maxCurrentPlacesNumber,
            textStyle = TextStyle(fontSize = 15.sp),
            onValueChange = onValueChange
        )
    }
}

@Composable
fun MapTypeSetting(
    expanded: Boolean,
    selectedValue: String,
    onDropdownMenuExpand: () -> Unit,
    onDropdownMenuDismiss: () -> Unit,
    onDropdownMenuItemSelect: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
            .drawBehind {
                val borderSize = 1.dp
                drawLine(
                    color = Color.White,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderSize.toPx(),
                    pathEffect = PathEffect.cornerPathEffect(5.0f)
                )
            },
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Map type: "
        )
        Box {
            IconButton(onClick = onDropdownMenuExpand) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(selectedValue)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = onDropdownMenuDismiss,
            ) {
                DropdownMenuItem(
                    text = {
                        Text("Normal")
                    },
                    onClick = {
                        onDropdownMenuItemSelect("Normal")
                    },
                )
                DropdownMenuItem(
                    text = {
                        Text("Hybrid")
                    },
                    onClick = {
                        onDropdownMenuItemSelect("Hybrid")
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text("Terrain")
                    },
                    onClick = {
                        onDropdownMenuItemSelect("Terrain")
                    }
                )
            }
        }
    }
}

@Composable
fun TrafficSetting(
    isTrafficEnabled: Boolean,
    onTrafficChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp, top = 8.dp, bottom = 8.dp)
            .drawBehind {
                val borderSize = 1.dp
                drawLine(
                    color = Color.White,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderSize.toPx(),
                    pathEffect = PathEffect.cornerPathEffect(5.0f)
                )
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text("Enable traffic")
        Switch(
            checked = isTrafficEnabled,
            onCheckedChange = onTrafficChange
        )
    }
}