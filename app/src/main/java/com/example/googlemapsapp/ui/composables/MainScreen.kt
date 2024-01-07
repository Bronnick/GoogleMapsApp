package com.example.googlemapsapp.ui.composables

import androidx.annotation.StringRes
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
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
import com.example.googlemapsapp.utils.getRandomChar
import com.example.googlemapsapp.utils.mapTypeParam
import com.example.googlemapsapp.utils.trafficParam
import com.example.googlemapsapp.view_models.CurrentPlacesViewModel
import com.example.googlemapsapp.view_models.FavouritePlacesViewModel
import com.example.googlemapsapp.view_models.MapViewModel
import com.example.googlemapsapp.view_models.SettingsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

sealed class Screen(
    val route: String,
    val icon: ImageVector,
    @StringRes val resourceId: Int
){
    object Map : Screen(
        route = "home?name={name}&lat={lat}&lng={lng}&show={show}",
        icon = Icons.Filled.Map,
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

@OptIn(ExperimentalMaterial3Api::class)
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

    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            val route = navBackStackEntry?.destination?.route
            if(route?.subSequence(0, route.length.coerceAtMost(7)) == "details") {
                TopAppBar(
                    title = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        },
        bottomBar = {
            val route = navBackStackEntry?.destination?.route
            if(route?.subSequence(0, route.length.coerceAtMost(7)) != "details") {
                NavigationBar {
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = null) },
                            label = { Text(text = stringResource(screen.resourceId)) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.imePadding()
            )
        }
    ) { it ->
        val onShowOnMapButtonClick: (Place) -> Unit = { place ->
            navController.navigate(
                Screen.Map.route.replace(
                    oldValue = "{name}",
                    newValue = place.name.toString()
                ).replace(
                    oldValue = "{lat}",
                    newValue = place.latitude.toString()
                ).replace(
                    oldValue = "{lng}",
                    newValue = place.longitude.toString()
                ).replace(
                    oldValue = "{show}",
                    newValue = true.toString()
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
                route = Screen.DetailsScreen.route.replace(
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
                ),

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
                route = Screen.Map.route,
                arguments = listOf(
                    navArgument("name"){
                        type = NavType.StringType
                        defaultValue = " "
                    },
                    navArgument("lat"){
                        type = NavType.FloatType
                        defaultValue = 44.810058
                    },
                    navArgument("lng"){
                        type = NavType.FloatType
                        defaultValue = 20.4617586
                    },
                    navArgument("show"){
                        type = NavType.BoolType
                        defaultValue = false
                    }
                ),
                enterTransition = {
                    EnterTransition.None
                },
                exitTransition = {
                    ExitTransition.None
                },
                popEnterTransition = {
                    EnterTransition.None
                },
                popExitTransition = {
                    ExitTransition.None
                }
            ) { navBackStackEntry ->
                MapScreen(
                    viewModel = mapViewModel,
                    name = navBackStackEntry.arguments?.getString("name") ?: " ",
                    latitude = navBackStackEntry.arguments?.getFloat("lat")!!,
                    longitude = navBackStackEntry.arguments?.getFloat("lng")!!,
                    showCurrentPositionMarker = navBackStackEntry.arguments?.getBoolean("show") ?: false
                )
            }
            composable(
                route = Screen.CurrentPlaces.route,
                enterTransition = {
                    EnterTransition.None
                },
                exitTransition = {
                    ExitTransition.None
                },
                popEnterTransition = {
                    EnterTransition.None
                },
                popExitTransition = {
                    ExitTransition.None
                }
            ) {
                CurrentPlacesScreen(
                    viewModel = currentPlacesViewModel,
                    onShowOnMapButtonClick = onShowOnMapButtonClick,
                    onViewDetailsButtonClick = onViewDetailsButtonClick
                )
            }
            composable(
                route = Screen.FavoritePlaces.route,
                enterTransition = {
                    EnterTransition.None
                },
                exitTransition = {
                    ExitTransition.None
                },
                popEnterTransition = {
                    EnterTransition.None
                },
                popExitTransition = {
                    ExitTransition.None
                }
            ) {
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
                route = Screen.DetailsScreen.route,
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
                ),
                enterTransition = {
                    EnterTransition.None
                },
                exitTransition = {
                    ExitTransition.None
                },
                popEnterTransition = {
                    EnterTransition.None
                },
                popExitTransition = {
                    ExitTransition.None
                }
            ) { navBackStackEntry ->
                DetailsScreen(
                    name = navBackStackEntry.arguments?.getString("name") ?: " ",
                    photoRef = navBackStackEntry.arguments?.getString("photoRef") ?: " ",
                    address = navBackStackEntry.arguments?.getString("address") ?: " ",
                    rating = (navBackStackEntry.arguments?.getFloat("rating"))?.toDouble() ?: 0.0
                )
            }
            composable(
                route = Screen.Settings.route,
                enterTransition = {
                    EnterTransition.None
                },
                exitTransition = {
                    ExitTransition.None
                },
                popEnterTransition = {
                    EnterTransition.None
                },
                popExitTransition = {
                    ExitTransition.None
                }
            ) {
                SettingsScreen(
                    viewModel = settingsViewModel,
                    onMapTypeChanged = { mapType ->
                        settingsViewModel.updateSettings(mapTypeParam, mapType)
                        settingsViewModel.setMapTypeDropdownMenuVisibility(false)
                        mapViewModel.setMapType(mapType)
                    },
                    onTrafficChange = { isTrafficEnabled ->
                        settingsViewModel.updateSettings(trafficParam, isTrafficEnabled)
                        settingsViewModel.setTraffic(isTrafficEnabled)
                        mapViewModel.setTraffic(isTrafficEnabled)
                    },
                    onInvalidValueEnter = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Invalid value entered!")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ConstructedHeader(
    text: String
) {
    var changeNum = 3

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        text.forEach {
            if(it != ' ') CharContainer(c = it.uppercaseChar(), changeNum++)
            else Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
fun CharContainer(
    c: Char,
    changeNum: Int
) {
    var value by remember { mutableStateOf(getRandomChar()) }

    LaunchedEffect(Unit) {
        for(i in 0 until changeNum) {
            value = getRandomChar()
            delay(50)
        }
        value = c
    }

    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.onTertiary)
            .widthIn(16.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Text(
            modifier = Modifier.padding(all = 4.dp),
            text = value.toString(),
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
