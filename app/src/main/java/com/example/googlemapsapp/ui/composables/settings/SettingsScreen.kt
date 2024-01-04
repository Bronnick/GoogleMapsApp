package com.example.googlemapsapp.ui.composables.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.googlemapsapp.utils.mapTypeParam
import com.google.maps.android.compose.MapType

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onMapTypeChanged: (String) -> Unit,
    onTrafficChange: (Boolean) -> Unit,
    onInvalidValueEnter: () -> Unit
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
                    if(it.length in 1..2)
                        viewModel.updateSettings(maxCurrentPlacesNumberParam, it.toInt())
                    else
                        throw NumberFormatException()
                    it
                } catch(e: NumberFormatException) {
                    onInvalidValueEnter()
                    maxCurrentPlacesNumber
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
            text = "Max current places: ",
            style = MaterialTheme.typography.bodyLarge
        )
        OutlinedTextField(
            modifier = Modifier.widthIn(max = 50.dp),
            value = maxCurrentPlacesNumber,
            textStyle = MaterialTheme.typography.bodyLarge,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true
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
            text = "Map type: ",
            style = MaterialTheme.typography.bodyLarge
        )
        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedValue,
                    style = MaterialTheme.typography.bodyMedium
                )
                IconButton(
                    modifier = Modifier.widthIn(max = 25.dp),
                    onClick = onDropdownMenuExpand
                ) {
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
                        Text(
                            text = "Normal",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onDropdownMenuItemSelect("Normal")
                    },
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "Hybrid",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onDropdownMenuItemSelect("Hybrid")
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "Terrain",
                            style = MaterialTheme.typography.bodyMedium
                        )
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
        modifier = Modifier
            .fillMaxWidth()
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
        Text(
            text = "Enable traffic: ",
            style = MaterialTheme.typography.bodyLarge
        )
        Switch(
            checked = isTrafficEnabled,
            onCheckedChange = onTrafficChange,
            colors = SwitchDefaults.colors()
        )
    }
}