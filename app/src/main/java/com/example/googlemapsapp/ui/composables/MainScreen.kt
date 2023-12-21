package com.example.googlemapsapp.ui.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.googlemapsapp.R
import com.example.googlemapsapp.view_models.CurrentPlacesViewModel
import com.example.googlemapsapp.view_models.SettingsViewModel

sealed class Screen(
    val route: String,
    val icon: ImageVector,
    @StringRes val resourceId: Int
){
    object Map : Screen(
        route ="home",
        icon = Icons.Filled.Email,
        resourceId = R.string.map_bottom_nav
    )
    object CurrentPlaces : Screen(
        route = "current_places",
        icon = Icons.Filled.Place,
        resourceId = R.string.current_places_bottom_nav
    )
    object Settings : Screen(
        route = "settings",
        icon = Icons.Filled.Settings,
        resourceId = R.string.settings
    )
}

val items = listOf(
    Screen.Map,
    Screen.CurrentPlaces,
    Screen.Settings
)

@Composable
fun MainScreen(
    currentPlacesViewModel: CurrentPlacesViewModel,
    settingsViewModel: SettingsViewModel
){

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            BottomNavigation {

                items.forEach{screen ->
                    BottomNavigationItem(
                        icon = {Icon(screen.icon, contentDescription = null)},
                        label = {Text(text = stringResource(screen.resourceId))},
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route){
                                popUpTo(navController.graph.findStartDestination().id){
                                    saveState  = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Map.route,
            modifier = Modifier.padding(it)
        ) {
            composable(Screen.Map.route) {
                MapScreen()
            }
            composable(Screen.CurrentPlaces.route) {
                CurrentPlacesScreen(
                    currentPlacesUiState = currentPlacesViewModel.currentPlacesUiState
                )
            }
            composable(Screen.Settings.route) {
                SettingsScreen(settingsViewModel)
            }
        }
    }
}