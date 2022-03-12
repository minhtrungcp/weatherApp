package com.example.weatherapp.ui.detail_weather_city

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.Constant.PARAM_CITY_NAME
import com.example.weatherapp.common.Resource
import com.example.weatherapp.domain.model.weather.ForecastCity
import com.example.weatherapp.domain.model.weather.WeatherCity
import com.example.weatherapp.domain.use_case.GetLocationUseCase
import com.example.weatherapp.domain.use_case.GetWeatherCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailWeatherViewModel @Inject constructor(
    private val getWeatherCityUseCase: GetWeatherCityUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _forecastCity = mutableStateOf(ForecastCity())
    val forecastCity: State<ForecastCity> = _forecastCity
    private val _error = mutableStateOf("")
    val error: State<String> = _error
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading
    var cityName: String? = null

    init {
        getForecastCity()
    }

    fun refresh() {
        getForecastCity()
    }

    fun getForecastCity() {
        savedStateHandle.get<String>(PARAM_CITY_NAME)?.let { cityName ->
            this.cityName = cityName
            getWeatherCityUseCase.findWeatherForecastDataByCity(cityName).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _forecastCity.value = result.data!!
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

    }

}
