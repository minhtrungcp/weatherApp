package com.example.weatherapp.ui.favorite_city

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteCityViewModel @Inject constructor(
    private val getLocalUseCase: GetLocalUseCase
) : ViewModel() {

    private val _favoriteCities = mutableStateOf(listOf<CityModel>())
    val favoriteCities: State<List<CityModel>> = _favoriteCities
    private val _error = mutableStateOf("")
    val error: State<String> = _error
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        getFavoriteCities()
    }

    fun refresh() {
        getFavoriteCities()
    }

    private fun getFavoriteCities() {
        getLocalUseCase.getAllCities().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _favoriteCities.value = result.data!!
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
