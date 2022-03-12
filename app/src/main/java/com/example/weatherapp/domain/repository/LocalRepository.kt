package com.example.weatherapp.domain.repository

import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.local.CityModel
import com.example.weatherapp.domain.model.weather.WeatherCity
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun insertCity(city : CityModel) : Long
    fun getCities() : Flow<Resource<List<CityModel>>>
    suspend fun deleteCity(id : Int)
    suspend fun deleteAllCities()
}