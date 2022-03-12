package com.example.weatherapp.data.model

import com.example.weatherapp.domain.model.weather.Main

data class MainDTO(
    val temp: Double? = null,
    val humidity: Double? = null
)

fun MainDTO.toMain() = Main(
    temp = temp,
    humidity = humidity
)