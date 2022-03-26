package com.example.topupmama.base

import com.example.topupmama.TopUpMama
import com.example.topupmama.data.local.WeatherDao

class BaseApplication(private val weatherDao: WeatherDao) : TopUpMama() {


    override fun onCreate() {
        super.onCreate()

//        CoroutineScope(context = Dispatchers.IO).launch {
//            weatherDao.insertCountries(CountriesManager.getCountries())
//        }

    }








}