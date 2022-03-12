package com.example.weatherapp.data.model

import com.example.weatherapp.domain.model.weather.WeatherCity


data class WeatherCityResponse(
    val id: Int,
    val name: String? = null,
    val main: MainDTO? = null,
    val weather: List<WeatherItemDTO?>? = null,
    val wind: WindDTO? = null
)

fun WeatherCityResponse.toWeatherCity() = WeatherCity(
    id = id,
    name = name,
    main = main?.toMain(),
    weather = weather?.map { it?.toWeatherItem() },
    wind = wind?.toWind()
)
