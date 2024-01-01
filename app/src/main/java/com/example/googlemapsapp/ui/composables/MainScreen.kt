package com.example.googlemapsapp.ui.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.googlemapsapp.R
import com.example.googlemapsapp.classes.Place
import com.example.googlemapsapp.ui.composables.current_places.CurrentPlacesScreen
import com.example.googlemapsapp.ui.composables.favorites.FavouritePlacesScreen
import com.example.googlemapsapp.ui.composables.map.MapScreen
import com.example.googlemapsapp.ui.composables.place_details.DetailsScreen
import com.example.googlemapsapp.ui.composables.settings.SettingsScreen
import com.example.googlemapsapp.view_models.CurrentPlacesViewModel
import com.example.googlemapsapp.view_models.FavouritePlacesViewModel
import com.example.googlemapsapp.view_models.MapViewModel
import com.example.googlemapsapp.view_models.SettingsViewModel

sealed class Screen(
    val route: String,
    val icon: ImageVector,
    @StringRes val resourceId: Int
){
    object Map : Screen(
        route = "home?lat={lat}&lng={lng}",
        icon = Icons.Filled.Email,
        resourceId = R.string.map_bottom_nav
    )
    object CurrentPlaces : Screen(
        route = "current_places",
        icon = Icons.Filled.Place,
        resourceId = R.string.current_places_bottom_nav
    )
    object FavoritePlaces : Screen(
        route = "favorite_places",
        icon = Icons.Filled.Favorite,
        resourceId = R.string.favorite_places_bottom_nav
    )
    object DetailsScreen : Screen(
        route = "details?name={name}&photoRef={photoRef}&address={address}&rating={rating}",
        icon = Icons.Filled.Check,
        resourceId = R.string.details_bottom_nav
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
    Screen.FavoritePlaces,
    Screen.Settings
)

@Composable
fun MainScreen(
    mapViewModel: MapViewModel,
    currentPlacesViewModel: CurrentPlacesViewModel,
    favouritePlacesViewModel: FavouritePlacesViewModel,
    settingsViewModel: SettingsViewModel
) {

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
        val onShowOnMapButtonClick: (Place) -> Unit = { place ->
            navController.navigate(
                Screen.Map.route.replace(
                    oldValue = "{lat}",
                    newValue = place.latitude.toString()
                ).replace(
                    oldValue = "{lng}",
                    newValue = place.longitude.toString()
                )
            ) {
                popUpTo(navController.graph.findStartDestination().id){
                    saveState  = true
                }
                launchSingleTop = true
                //restoreState = true
            }
        }

        val onViewDetailsButtonClick: (Place) -> Unit = { place ->
            navController.navigate(
                Screen.DetailsScreen.route.replace(
                    oldValue = "{name}",
                    newValue = place.name ?: ""
                ).replace(
                    oldValue = "{photoRef}",
                    newValue = place.photoRef ?: ""
                ).replace(
                    oldValue = "{address}",
                    newValue = place.address ?: ""
                ).replace(
                    oldValue = "{rating}",
                    newValue = (place.rating ?: 0.0).toString()
                )
            ) {
                /*popUpTo(navController.graph.findStartDestination().id){
                    saveState  = true
                }*/
                launchSingleTop = true
                //restoreState = true
            }
        }

        NavHost(
            navController = navController,
            startDestination = Screen.CurrentPlaces.route,
            modifier = Modifier.padding(it)
        ) {
            composable(
                Screen.Map.route,
                arguments = listOf(
                    navArgument("lat"){
                        type = NavType.FloatType
                        defaultValue = 44.810058
                    },
                    navArgument("lng"){
                        type = NavType.FloatType
                        defaultValue = 20.4617586
                    }
                )
            ) { navBackStackEntry ->
                MapScreen(
                    viewModel = mapViewModel,
                    latitude = navBackStackEntry.arguments?.getFloat("lat")!!,
                    longitude = navBackStackEntry.arguments?.getFloat("lng")!!
                )
            }
            composable(Screen.CurrentPlaces.route) {
                CurrentPlacesScreen(
                    viewModel = currentPlacesViewModel,
                    onShowOnMapButtonClick = onShowOnMapButtonClick,
                    onViewDetailsButtonClick = onViewDetailsButtonClick
                )
            }
            composable(Screen.FavoritePlaces.route) {
                FavouritePlacesScreen(
                    viewModel = favouritePlacesViewModel,
                    onShowOnMapButtonClick = onShowOnMapButtonClick,
                    onViewDetailsButtonClick = onViewDetailsButtonClick,
                    onDeleteFavoritePlace = { place ->
                        favouritePlacesViewModel.deletePlace(place)
                        currentPlacesViewModel.refreshList()
                    }
                )
            }
            composable(
                Screen.DetailsScreen.route,
                arguments = listOf(
                    navArgument("name"){
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument("photoRef"){
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument("address"){
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument("rating"){
                        type = NavType.FloatType
                        defaultValue = 0.0
                    }
                )
            ) { navBackStackEntry ->
                DetailsScreen(
                    name = navBackStackEntry.arguments?.getString("name") ?: " ",
                    photoRef = navBackStackEntry.arguments?.getString("photoRef") ?: " ",
                    address = navBackStackEntry.arguments?.getString("address") ?: " ",
                    rating = (navBackStackEntry.arguments?.getFloat("rating"))?.toDouble() ?: 0.0
                )
            }
            composable(Screen.Settings.route) {
                SettingsScreen(settingsViewModel)
            }
        }
    }
}