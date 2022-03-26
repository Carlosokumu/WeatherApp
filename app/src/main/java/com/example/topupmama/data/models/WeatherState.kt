package com.example.topupmama.data.models

import com.example.topupmama.data.local.entities.Country

sealed class WeatherState {



    object Loading : WeatherState()


    data class Result(val weather: Country) : WeatherState()


    data class Error(val message: String) : WeatherState()
}