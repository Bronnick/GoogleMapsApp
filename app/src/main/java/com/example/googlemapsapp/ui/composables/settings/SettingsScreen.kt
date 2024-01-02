package com.example.googlemapsapp.ui.composables.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.unit.dp
import com.example.googlemapsapp.utils.mapTypeParam
import com.google.maps.android.compose.MapType

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onMapTypeChanged: (String) -> Unit
) {
    var maxCurrentPlacesNumber by remember {
        mutableStateOf(viewModel.maxCurrentPlacesNumber.toString())
    }

    val showMapTypeDropdownMenu = viewModel.showMapTypeDropdownMenu.observeAsState().value
    val selectedDropdownMenuValue = viewModel.selectedMapTypeValue.observeAsState().value

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
    }
}

@Composable
fun MaxCurrentPlacesSetting(
    maxCurrentPlacesNumber: String,
    onValueChange: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
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
        modifier = Modifier.fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Map type: "
        )
        Box {
            IconButton(onClick = onDropdownMenuExpand) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
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
                    onClick = {
                        onDropdownMenuItemSelect("Normal")
                    },
                ) {
                    Text("Normal")
                }
                DropdownMenuItem(
                    onClick = {
                        onDropdownMenuItemSelect("Hybrid")
                    }
                ) {
                    Text("Hybrid")
                }
            }
        }
    }

}