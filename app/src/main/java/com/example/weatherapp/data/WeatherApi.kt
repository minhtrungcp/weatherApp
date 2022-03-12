package com.example.weatherapp.data

import com.example.weatherapp.common.Constant
import com.example.weatherapp.data.model.ForecastCityResponse
import com.example.weatherapp.data.model.WeatherCityResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeatherByCityName(
        @Query("q") q: String,
        @Query("units") units: String = Constant.WEATHER_UNIT,
        @Query("appid") appid: String = Constant.WEATHER_API_KEY
    ): WeatherCityResponse

    @GET("weather")
    suspend fun getWeatherByGeoLocation(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("units") units: String = Constant.WEATHER_UNIT,
        @Query("appid") appid: String = Constant.WEATHER_API_KEY
    ): WeatherCityResponse

    @GET("forecast")
    suspend fun findWeatherForecastDataByCity(
        @Query("q") q: String,
        @Query("cnt") cnt: String = Constant.CNT_UNIT,
        @Query("units") units: String = Constant.WEATHER_UNIT,
        @Query("appid") appid: String = Constant.WEATHER_API_KEY
    ): ForecastCityResponse
}