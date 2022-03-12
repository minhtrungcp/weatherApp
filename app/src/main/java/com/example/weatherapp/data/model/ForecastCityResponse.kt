package com.example.weatherapp.data.model

import com.example.weatherapp.domain.model.weather.ForecastCity

data class ForecastCityResponse(
    val cnt: Int? = null,
    val cod: String? = null,
    val message: Int? = null,
    val list: List<ForecastItemDTO>? = null
)

fun ForecastCityResponse.toForecastCity() = ForecastCity(
    cnt = cnt,
    cod = cod,
    message= message,
    list = list?.map { it?.toForecastItem() }
)
