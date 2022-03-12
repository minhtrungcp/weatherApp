package com.example.weatherapp.data.repository

import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.WeatherApi
import com.example.weatherapp.data.model.toForecastCity
import com.example.weatherapp.data.model.toWeatherCity
import com.example.weatherapp.domain.model.weather.ForecastCity
import com.example.weatherapp.domain.model.weather.WeatherCity
import com.example.weatherapp.domain.repository.WeatherCityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherCityRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherCityRepository {
    override fun getWeatherByCityName(cityName: String): Flow<Resource<WeatherCity>> = flow {
        try {
            emit(Resource.Loading<WeatherCity>())
            val response = api.getWeatherByCityName(cityName)
            emit(Resource.Success<WeatherCity>(response.toWeatherCity()))
        } catch (e: HttpException) {
            emit(
                Resource.Error<WeatherCity>(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<WeatherCity>("Couldn't reach server, check your internet"))
        }
    }

    override fun getWeatherByGeoLocation(
        latitude: String,
        longitude: String
    ): Flow<Resource<WeatherCity>> = flow {
        try {
            emit(Resource.Loading<WeatherCity>())
            val response = api.getWeatherByGeoLocation(latitude, longitude)
            emit(Resource.Success<WeatherCity>(response.toWeatherCity()))
        } catch (e: HttpException) {
            emit(
                Resource.Error<WeatherCity>(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<WeatherCity>("Couldn't reach server, check your internet"))
        }
    }

    override fun findWeatherForecastDataByCity(cityName: String): Flow<Resource<ForecastCity>> =
        flow {
            try {
                emit(Resource.Loading<ForecastCity>())
                val response = api.findWeatherForecastDataByCity(cityName)
                emit(Resource.Success<ForecastCity>(response.toForecastCity()))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<ForecastCity>(
                        e.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<ForecastCity>("Couldn't reach server, check your internet"))
            }
        }
}