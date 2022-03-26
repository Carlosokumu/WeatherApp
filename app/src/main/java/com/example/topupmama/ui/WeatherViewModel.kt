package com.example.topupmama.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topupmama.data.local.entities.Country
import com.example.topupmama.data.models.WeatherState
import com.example.topupmama.data.repository.WeatherRepository
import com.example.topupmama.mappers.asCountry
import com.example.topupmama.network.WeatherResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    @ExperimentalCoroutinesApi
    private val _mutableweatherState: MutableStateFlow<WeatherState> =
        MutableStateFlow(WeatherState.Loading)

    @ExperimentalCoroutinesApi
    val mutableWeatherState: StateFlow<WeatherState> = _mutableweatherState

    private val countries: MutableLiveData<List<Country>> =
        MutableLiveData()
    val countriesModel: LiveData<List<Country>>
        get() = countries


    init {
//        viewModelScope.launch {
//            weatherRepository.saveCountriesWithConditions(CountriesManager.getCountries())
//        }
//        viewModelScope.launch {
//            fetchAllCountries()
//        }
        Log.d("INITCALLED","init")
    }


    @ExperimentalCoroutinesApi
    fun fetchCurrentWeather(country: Country) = viewModelScope.launch {
        when (
            val result =
                weatherRepository.fetchCurrentWeather(country.cityName)
        ) {
            WeatherResult.WeatherError -> {

                _mutableweatherState.value = WeatherState.Error("couldn't update weather")


            }
            is WeatherResult.ServerError -> {
                //mutableWeatherState.value =
                //WeatherState.Error(result.errorBody?.message.toString())
            }
            is WeatherResult.Success -> {
                _mutableweatherState.value = WeatherState.Result(result.data.asCountry(country))
            }
        }
    }


    fun insertCountry(country: Country) = viewModelScope.launch {
        weatherRepository.insertCountry(country)
    }


     fun fetchAllCountries() = viewModelScope.launch {
        val fetchedCountries = weatherRepository.getCountriesWithConditions()
         for (i in fetchedCountries){
             Log.d("PODUCER", i.isFavorite.toString())
         }
        countries.postValue(fetchedCountries)

    }





        fun saveCountries() = viewModelScope.launch {
            //weatherRepository.saveCountriesWithConditions(CountriesManager.getCountries())
        }





}



