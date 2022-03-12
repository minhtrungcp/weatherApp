package com.example.weatherapp.domain.model.weather

data class ForecastCity(
    val cnt: Int? = null,
    val cod: String? = null,
    val message: Int? = null,
    val list: List<ForecastItem>? = null
)
