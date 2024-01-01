package com.example.googlemapsapp.ui.composables.settings

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import com.example.googlemapsapp.utils.maxCurrentPlacesNumberParam
import com.example.googlemapsapp.utils.settingTextParam
import com.example.googlemapsapp.view_models.SettingsViewModel
import androidx.compose.runtime.*

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel
) {
    var maxCurrentPlacesNumber by remember {
        mutableStateOf(viewModel.maxCurrentPlacesNumber.toString())
    }

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
        MapTypeSetting()
    }
}

@Composable
fun MaxCurrentPlacesSetting(
    maxCurrentPlacesNumber: String,
    onValueChange: (String) -> Unit
) {
    Row {
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
fun MapTypeSetting() {
    Text(
        text = "Map type: "
    )

}