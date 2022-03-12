package com.example.weatherapp.ui.favorite_city

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.weatherapp.common.Constant.WEATHER_API_IMAGE_ENDPOINT
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import com.example.weatherapp.ui.Screen
import com.example.weatherapp.ui.detail_weather_city.cell.ForecastCityCell

@Composable
fun FavoriteCityScreen(
    navController: NavController,
    viewModel: FavoriteCityViewModel = hiltViewModel()
) {
    val favoriteCities = viewModel.favoriteCities.value
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.refresh()
            },
        ) {
            if (favoriteCities.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(favoriteCities?.size) { index ->
                        val city = favoriteCities[index]
                        Card(
                            shape = MaterialTheme.shapes.small,
                            modifier = Modifier
                                .padding(6.dp)
                                .fillMaxWidth()
                                .clickable {
                                    if (!city?.name.isNullOrEmpty()) {
                                    navController.navigate(Screen.WeatherCityDetailScreen.route + "/${city.name}")
                                } },
                            elevation = 8.dp
                        ) {
                            city?.name?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.h2,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    modifier = Modifier.padding(start = 6.dp, end = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
            if (viewModel.error.value.isNotBlank()) {
                Text(
                    text = viewModel.error.value,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            } else
                if (viewModel.isLoading.value) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
        }
    }
}