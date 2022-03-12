package com.example.weatherapp.domain.model.weather

data class WeatherCity(
    val id: Int = 0,
    val name: String? = null,
    val main: Main? = null,
    val weather: List<WeatherItem?>? = null,
    val wind: Wind? = null
)
