package com.example.weatherapp.domain.use_case

import com.example.weatherapp.common.Resource
import com.example.weatherapp.domain.model.weather.ForecastCity
import com.example.weatherapp.domain.model.weather.WeatherCity
import com.example.weatherapp.domain.repository.WeatherCityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherCityUseCase @Inject constructor(
    private val repository: WeatherCityRepository
) {
    fun getWeatherByCityName(cityName: String): Flow<Resource<WeatherCity>> =
        repository.getWeatherByCityName(cityName)

    fun getWeatherByGeoLocation(
        latitude: String,
        longitude: String
    ): Flow<Resource<WeatherCity>> =
        repository.getWeatherByGeoLocation(latitude, longitude)

    fun findWeatherForecastDataByCity(cityName: String): Flow<Resource<ForecastCity>> =
        repository.findWeatherForecastDataByCity(cityName)
}