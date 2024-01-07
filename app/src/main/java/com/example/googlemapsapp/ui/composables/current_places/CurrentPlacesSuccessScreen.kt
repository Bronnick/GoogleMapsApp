package com.example.googlemapsapp.ui.composables.current_places

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.googlemapsapp.classes.Place
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import com.example.googlemapsapp.ui.composables.place_info.PlaceOverview
import com.example.googlemapsapp.view_models.CurrentPlacesUiState
import com.example.googlemapsapp.view_models.CurrentPlacesViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CurrentPlacesSuccessScreen(
    viewModel: CurrentPlacesViewModel,
    onShowOnMapButtonClick: (Place) -> Unit,
    onViewDetailsButtonClick: (Place) -> Unit,
) {
    val placeList = (viewModel.currentPlacesUiState as CurrentPlacesUiState.Success).places

    val isRefreshing by viewModel.isRefreshing.observeAsState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing ?: false),
        onRefresh = {
            viewModel.refresh()
        }
    ) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier.fillMaxSize()
        ) {
            items(placeList) { place ->
                PlaceOverview(
                    place = place,
                    onFavoritesIconClick = {
                        viewModel.changePlaceFavoriteStatus(place)
                    },
                    onShowOnMapButtonClick = onShowOnMapButtonClick,
                    onViewDetailsButtonClick = onViewDetailsButtonClick,
                    isFavoriteScreen = true
                )
            }
        }
    }

}