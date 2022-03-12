package com.example.weatherapp.domain.repository

import com.example.weatherapp.common.Resource
import com.example.weatherapp.domain.model.weather.ForecastCity
import com.example.weatherapp.domain.model.weather.WeatherCity
import kotlinx.coroutines.flow.Flow

interface WeatherCityRepository {
    fun getWeatherByCityName(cityName: String): Flow<Resource<WeatherCity>>
    fun getWeatherByGeoLocation(latitude: String,
                                longitude: String): Flow<Resource<WeatherCity>>
    fun findWeatherForecastDataByCity(cityName: String): Flow<Resource<ForecastCity>>
}