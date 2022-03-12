package com.example.weatherapp.ui.search_weather_city

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import com.example.weatherapp.ui.Screen
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WeatherSearchScreen(
    navController: NavController,
    viewModel: WeatherSearchViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val weatherCity = viewModel.weatherCity.value
    val weatherItem = viewModel.weatherCity.value.weather?.first()
    val weatherMain = viewModel.weatherCity.value.main
    val weatherWind = viewModel.weatherCity.value.wind
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    val coroutineScope = rememberCoroutineScope()
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val textState = remember { mutableStateOf(TextFieldValue()) }
                val keyboardController = LocalSoftwareKeyboardController.current
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = "Search city name here") },
                    value = textState.value,
                    onValueChange = { textState.value = it },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            viewModel.getWeatherCityByName(textState.value.text)
                            keyboardController?.hide()
                        })
                )

                if (weatherItem != null && viewModel.error.value.isBlank()) {
                    Card(
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier
                            .padding(6.dp)
                            .fillMaxWidth()
                            .clickable {
                                if (!weatherCity.name.isNullOrEmpty()) {
                                    navController.navigate(Screen.WeatherCityDetailScreen.route + "/${weatherCity.name}")
                                }
                            },
                        elevation = 8.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                weatherCity.name?.let {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.h3,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1
                                    )
                                }
                                weatherItem.description?.let {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.h3,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1
                                    )
                                }
                                val painter =
                                    rememberImagePainter(
                                        data = "${WEATHER_API_IMAGE_ENDPOINT}${weatherItem.icon}${"@4x.png"}",
                                    )

                                Image(
                                    painter = painter,
                                    contentDescription = null
                                )
                                Button(
                                    onClick = {
                                        coroutineScope.launch {
                                            var favorite = viewModel.addFavoriteCity()
                                            Toast.makeText(
                                                context,
                                                if (favorite > 0)  "Favorite city successful" else "This city already favorited",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    },
                                    colors = ButtonDefaults.textButtonColors(
                                        backgroundColor = Color.Green
                                    )
                                ) {
                                    Text("Favorite")
                                }
                            }
                            Column(
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                ) {
                                    Text(
                                        text = "Temp: ",
                                        style = MaterialTheme.typography.h3,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1
                                    )
                                    weatherMain?.temp?.let {
                                        Text(
                                            text = it.toString(),
                                            style = MaterialTheme.typography.h3,
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 1
                                        )
                                    }
                                    Text(
                                        text = " C",
                                        style = MaterialTheme.typography.h3,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                ) {
                                    Text(
                                        text = "Humidity: ",
                                        style = MaterialTheme.typography.h3,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1
                                    )
                                    weatherMain?.humidity?.let {
                                        Text(
                                            text = it.toString(),
                                            style = MaterialTheme.typography.h3,
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 1
                                        )
                                    }
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                ) {
                                    Text(
                                        text = "Wind: ",
                                        style = MaterialTheme.typography.h3,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1
                                    )
                                    weatherWind?.speed?.let {
                                        Text(
                                            text = it.toString(),
                                            style = MaterialTheme.typography.h3,
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 1
                                        )
                                    }
                                    Text(
                                        text = " m/s",
                                        style = MaterialTheme.typography.h3,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }
            }
            if (viewModel.error.value.isNotBlank()) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = viewModel.error.value,
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    )
                    Button(
                        onClick = {
                            viewModel.refresh()
                        },
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = Color.Gray
                        )
                    ) {
                        Text("Retry")
                    }
                }
            } else
                if (viewModel.isLoading.value) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
        }
    }
}
