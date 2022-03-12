package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun fetchLocation(): Flow<Location>
}