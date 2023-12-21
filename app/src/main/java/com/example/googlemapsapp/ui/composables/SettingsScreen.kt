package com.example.googlemapsapp.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.example.googlemapsapp.view_models.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel
){
    Text(
        modifier = Modifier.clickable {
            viewModel.updateSettingText(viewModel.settingText + "1")
        },
        text=viewModel.settingText
    )
}