package com.example.weatherapp.domain.use_case

import com.example.weatherapp.common.Resource
import com.example.weatherapp.domain.model.location.Location
import com.example.weatherapp.domain.model.weather.WeatherCity
import com.example.weatherapp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val repository: LocationRepository
) {
    fun fetchLocation(): Flow<Location> =
        repository.fetchLocation()
}