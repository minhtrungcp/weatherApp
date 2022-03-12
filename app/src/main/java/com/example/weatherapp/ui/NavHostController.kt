package com.example.weatherapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.ui.detail_weather_city.DetailWeatherScreen
import com.example.weatherapp.ui.main.MainScreen


@Composable
fun NavHostController(
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        composable(
            route = Screen.MainScreen.route
        ) {
            MainScreen(navController)
        }
        composable(
            route = Screen.WeatherCityDetailScreen.route + "/{city_name}"
        ) {
            DetailWeatherScreen(navController)
        }
    }
}