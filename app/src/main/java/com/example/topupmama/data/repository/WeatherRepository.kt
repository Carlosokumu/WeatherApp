package com.example.topupmama.data.repository

import android.util.Log
import com.example.topupmama.data.local.WeatherDao
import com.example.topupmama.data.local.entities.Country
import com.example.topupmama.network.WeatherApi
import com.example.topupmama.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


class WeatherRepository(
    private val weatherApi: WeatherApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val weatherDao: WeatherDao

) {


    suspend fun fetchCurrentWeather(cityName: String) = safeApiCall(ioDispatcher) {
        return@safeApiCall weatherApi.fetchCurrentWeather(cityName = cityName)
    }



    suspend fun saveCountriesWithConditions(countries: List<Country>) = weatherDao.insertCountries(countries)



    suspend fun getCountriesWithConditions() = weatherDao.getAllCountries()



    suspend fun insertCountry(country: Country) = weatherDao.insertCountry(country)



    suspend fun updateCountry(country: Country) = weatherDao.update(country)


    suspend fun update(isFavorite: Boolean,priority: Int,cityName: String) = weatherDao.update(isFavorite,priority,cityName)

}