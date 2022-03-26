package com.example.topupmama.network

import com.example.topupmama.data.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {


    @GET("v1/current.json")
    suspend fun fetchCurrentWeather(@Query("key") key: String ="94d124c031ba4486b9c81847222402",@Query("q") cityName: String): WeatherResponse

}