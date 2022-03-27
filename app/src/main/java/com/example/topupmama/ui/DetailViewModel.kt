package com.example.topupmama.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.topupmama.data.local.entities.Country
import com.example.topupmama.data.models.ForeCastState
import com.example.topupmama.data.repository.WeatherRepository
import com.example.topupmama.network.WeatherResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailViewModel(private val weatherRepository: WeatherRepository): ViewModel() {


    @ExperimentalCoroutinesApi
    private val _mutableforecastState: MutableStateFlow<ForeCastState> =
        MutableStateFlow(ForeCastState.Loading)

    @ExperimentalCoroutinesApi
    val   forecastState: StateFlow<ForeCastState> = _mutableforecastState

    fun insertCountry(country: Country) = viewModelScope.launch {
        weatherRepository.insertCountry(country)
    }



    fun update(isFavorite: Boolean,priority: Int,cityName: String) = viewModelScope.launch {
        Log.d("Updating","Updating")
        weatherRepository.update(isFavorite,priority,cityName)
    }



    @ExperimentalCoroutinesApi
    fun fetchForeCastWeather(cityName: String, days:  Int) = viewModelScope.launch {
        when (
            val result =
                weatherRepository.getForeCastWeather(cityName, days = days)
        ) {
            WeatherResult.WeatherError -> {
                _mutableforecastState.value = ForeCastState.Error(message = "No Response")

            }
            is WeatherResult.ServerError -> {

            }
            is WeatherResult.Success -> {
                _mutableforecastState.value = ForeCastState.Result(result.data)
            }
        }
    }
    fun updateFav(isFavorite: Boolean,priority: Int,cityName: String)= runBlocking {
        weatherRepository.update(isFavorite,priority,cityName)
    }
}