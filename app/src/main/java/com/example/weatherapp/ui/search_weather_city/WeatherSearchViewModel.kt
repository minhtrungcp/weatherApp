package com.example.weatherapp.ui.search_weather_city

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.local.CityModel
import com.example.weatherapp.domain.model.weather.WeatherCity
import com.example.weatherapp.domain.use_case.GetLocalUseCase
import com.example.weatherapp.domain.use_case.GetLocationUseCase
import com.example.weatherapp.domain.use_case.GetWeatherCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WeatherSearchViewModel @Inject constructor(
    private val getWeatherCityUseCase: GetWeatherCityUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val getLocalUseCase: GetLocalUseCase,
) : ViewModel() {

    private val _weatherCity = mutableStateOf(WeatherCity())
    val weatherCity: State<WeatherCity> = _weatherCity
    private val _error = mutableStateOf("")
    val error: State<String> = _error
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading
    var searchCityName: String? = null

    init {
//        getWeatherCityByGeoLocation()
        getWeatherCityByName(searchCityName ?: "Hanoi")
    }

    fun refresh() {
        getWeatherCityByName( "Hanoi")
    }

     suspend fun addFavoriteCity() : Long {
         val insertCity = viewModelScope.async {
             getLocalUseCase.insertCity(CityModel(_weatherCity.value.name, _weatherCity.value.id))
         }
         return insertCity.await()
    }

    fun getWeatherCityByName(cityName: String) {
        searchCityName = cityName
        getWeatherCityUseCase.getWeatherByCityName(cityName).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _weatherCity.value = result.data!!
                    _isLoading.value = false
                    _error.value = ""
                }
                is Resource.Error -> {
                    _error.value = result.message ?: "An unexpected error occurred"

                }
                is Resource.Loading -> {
                    _isLoading.value = true
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getWeatherCityByGeoLocation() {
        getLocationUseCase.fetchLocation().onEach { location ->
            getWeatherCityUseCase.getWeatherByGeoLocation(location.latitude.toString(),
                location.longitude.toString()).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _weatherCity.value = result.data!!
                        _isLoading.value = false
                        _error.value = ""
                    }
                    is Resource.Error -> {
                        _error.value = result.message ?: "An unexpected error occurred"

                    }
                    is Resource.Loading -> {
                        _isLoading.value = true
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}
