package com.example.weatherapp.ui.detail_weather_city

import androidx.compose.foundation.*
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import com.example.weatherapp.R
import com.example.weatherapp.ui.detail_weather_city.cell.ForecastCityCell


@Composable
fun DetailWeatherScreen(
    navController: NavController,
    viewModel: DetailWeatherViewModel = hiltViewModel()
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    val forecastCity = viewModel.forecastCity.value
    val weatherItems = forecastCity.list
    Scaffold(
        topBar = { DetailTopBar(viewModel.cityName ?: "", navController) },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = {
                        viewModel.refresh()
                    },
                ) {
                    if (weatherItems?.isNotEmpty() == true) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            items(weatherItems?.size) { index ->
                                ForecastCityCell(weatherItems[index])
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
    )
}

@Composable
fun DetailTopBar(title: String, navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = null
                )
            }
        },
        actions = {
            Box(modifier = Modifier.size(width = 70.dp, height = 50.dp)) {}
        }
    )
}