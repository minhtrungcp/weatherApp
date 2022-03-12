package com.example.weatherapp.common

import com.example.weatherapp.BuildConfig

object Constant {
    const val WEATHER_UNIT = "metric"
    const val CNT_UNIT = "3"
    const val BASE_URL_RETROFIT_API: String = BuildConfig.WEATHER_API_URL
    const val WEATHER_API_KEY :String = BuildConfig.WEATHER_API_KEY
    const val REQUEST_TIME_OUT: Long = 60
    const val UPDATE_INTERVAL_SECS = 10L
    const val FASTEST_UPDATE_INTERVAL_SECS = 2L
    const val WEATHER_API_IMAGE_ENDPOINT = "http://openweathermap.org/img/wn/"
    const val PARAM_CITY_NAME = "city_name"
}