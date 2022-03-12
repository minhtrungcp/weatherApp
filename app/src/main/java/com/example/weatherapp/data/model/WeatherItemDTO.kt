package com.example.weatherapp.data.model

import com.example.weatherapp.domain.model.weather.WeatherItem

data class WeatherItemDTO(
    val icon: String? = null,
    val description: String? = null,
    val main: String? = null,
    val id: Int? = null
)

fun WeatherItemDTO.toWeatherItem() = WeatherItem(
    icon = icon,
    description = description,
    main = main,
    id = id
)