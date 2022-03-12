package com.example.weatherapp.ui

sealed class Screen(val route: String){
    object WeatherCityDetailScreen: Screen("weather_detail_screen")
    object MainScreen: Screen("main_screen")
}