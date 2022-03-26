package com.example.topupmama.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.topupmama.data.local.entities.Country
import com.example.topupmama.data.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(private val weatherRepository: WeatherRepository): ViewModel() {




    fun insertCountry(country: Country) = viewModelScope.launch {
        weatherRepository.insertCountry(country)
    }



    fun update(isFavorite: Boolean,priority: Int,cityName: String) = viewModelScope.launch {
        Log.d("Updating","Updating")
        weatherRepository.update(isFavorite,priority,cityName)
    }
}