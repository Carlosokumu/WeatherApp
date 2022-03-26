package com.example.topupmama.network


/**
 * A class that  represents the states of an I0 operation
 */

sealed class WeatherResult<out R> {


    data class Success<out T>(val data: T) : WeatherResult<T>()


    data class ServerError(
        val code: Int? = null,
    ) : WeatherResult<Nothing>()



    object WeatherError : WeatherResult<Nothing>()
}