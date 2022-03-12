package com.example.weatherapp.domain.model.weather

import java.util.*
import android.text.format.DateFormat
import java.text.SimpleDateFormat

data class ForecastItem(
    val dt: Long? = null,
    val weather: List<WeatherItem?>? = null,
    val main: Main? = null,
    val wind: Wind? = null,
)

fun ForecastItem.getDate() : String {
    val format = SimpleDateFormat("dd/MM/yyyy")
    dt?.let {
        return DateFormat.format("dd/MM/yyyy", Date(dt * 1000)).toString()
    }
    return format.format(Date())
}
