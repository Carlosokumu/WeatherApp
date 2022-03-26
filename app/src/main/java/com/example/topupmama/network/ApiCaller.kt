package com.example.topupmama.network

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): WeatherResult<T> = withContext(dispatcher) {

    try {
        WeatherResult.Success(apiCall.invoke())

    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException ->{
                WeatherResult.WeatherError

            }

            is HttpException ->{
                WeatherResult.ServerError(throwable.code())
            }
            else -> WeatherResult.ServerError(null)
        }

    }
}