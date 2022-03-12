package com.example.weatherapp.ui.detail_weather_city.cell

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.weatherapp.common.Constant
import com.example.weatherapp.domain.model.weather.ForecastCity
import com.example.weatherapp.domain.model.weather.ForecastItem
import com.example.weatherapp.domain.model.weather.getDate

@Composable
fun ForecastCityCell(forecastItem: ForecastItem) {

    val weatherItem = forecastItem.weather?.first()
    val weatherMain = forecastItem.main
    val weatherWind = forecastItem.wind
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .clickable { },
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
                val timeData = forecastItem.getDate()
                Text(
                    text = timeData,
                    style = MaterialTheme.typography.h3,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                weatherItem?.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.h3,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                val painter =
                    rememberImagePainter(
                        data = "${Constant.WEATHER_API_IMAGE_ENDPOINT}${weatherItem?.icon}${"@4x.png"}",
                    )

                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
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