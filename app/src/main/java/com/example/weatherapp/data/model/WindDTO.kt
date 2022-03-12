package com.example.weatherapp.data.model

import com.example.weatherapp.domain.model.weather.Wind

data class WindDTO(
    val speed: Double? = null,
    val deg: Double? = null
)

fun WindDTO.toWind() = Wind(
    speed = speed,
    deg = deg
)