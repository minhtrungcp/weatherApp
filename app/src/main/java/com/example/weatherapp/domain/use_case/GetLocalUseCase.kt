package com.example.weatherapp.domain.use_case


import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.local.CityModel
import com.example.weatherapp.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalUseCase @Inject constructor(
    private val repository: LocalRepository
) {
    suspend fun insertCity(city: CityModel): Long = repository.insertCity(city)

    fun getAllCities(): Flow<Resource<List<CityModel>>> = repository.getCities()

    suspend fun deleteAllCities() = repository.deleteAllCities()

    suspend fun deleteCity(id: Int) = repository.deleteCity(id)
}