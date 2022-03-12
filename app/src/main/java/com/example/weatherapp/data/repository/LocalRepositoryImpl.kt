package com.example.weatherapp.data.repository


import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.local.CityDao
import com.example.weatherapp.data.local.CityModel
import com.example.weatherapp.data.model.toForecastCity
import com.example.weatherapp.domain.model.weather.ForecastCity
import com.example.weatherapp.domain.repository.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val cityDao: CityDao
) : LocalRepository {

    override suspend fun deleteCity(id: Int) = withContext(Dispatchers.IO) {
        cityDao.deleteCity(id)
    }

    override suspend fun deleteAllCities() = withContext(Dispatchers.IO) {
        cityDao.deleteAllCities()
    }

    override suspend fun insertCity(city: CityModel) = withContext(Dispatchers.IO) {
        cityDao.insertCity(city)
    }

    override fun getCities(): Flow<Resource<List<CityModel>>> =
        flow {
            try {
                emit(Resource.Loading<List<CityModel>>())
                val response = cityDao.getAllCities()
                emit(Resource.Success<List<CityModel>>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<List<CityModel>>(
                        e.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<List<CityModel>>("Couldn't reach server, check your internet"))
            }
        }
}