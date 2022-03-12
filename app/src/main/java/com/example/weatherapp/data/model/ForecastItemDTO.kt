package com.example.weatherapp.data.model

import com.example.weatherapp.domain.model.weather.ForecastItem

data class ForecastItemDTO(
    val dt: Long? = null,
    val weather: List<WeatherItemDTO?>? = null,
    val main: MainDTO? = null,
    val wind: WindDTO? = null,
)

fun ForecastItemDTO.toForecastItem() = ForecastItem(
    dt = dt,
    weather = weather?.map { it?.toWeatherItem() },
    main = main?.toMain(),
    wind = wind?.toWind()
)
