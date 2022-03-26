package com.example.topupmama.work

import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.topupmama.base.CountriesBox
import com.example.topupmama.data.local.entities.Country
import com.example.topupmama.data.local.entities.FavoriteCountry
import com.example.topupmama.data.models.WeatherState
import com.example.topupmama.data.repository.WeatherRepository
import com.example.topupmama.mappers.asCountry
import com.example.topupmama.mappers.asNotification
import com.example.topupmama.network.WeatherApi
import com.example.topupmama.network.WeatherResult
import com.example.topupmama.utils.NotificationCraftman
import okhttp3.internal.notify
import org.koin.java.KoinJavaComponent.inject


class NotifyWorker(val context: Context, params: WorkerParameters): CoroutineWorker(context,params){


    val  weatherRepository : WeatherRepository by inject(WeatherRepository::class.java)

    override suspend fun doWork(): Result {
        Log.d("WorkDoing","Here Stared")
        val m = inputData.getStringArray("CITIES")
        val favoriteCountries = CountriesBox.store.boxFor(FavoriteCountry::class.java).all
        val cityNames = mutableListOf<String>()
        if (favoriteCountries.isNotEmpty()) {
            for (i in favoriteCountries) {
                cityNames.add(i.cityName)
            }
        }

        getFavoritesWeather(*cityNames.toTypedArray())

        return Result.success()
    }



   private suspend fun getFavoritesWeather(vararg cityNames: String){
       for (i in cityNames){
           when (
               val result =
                   weatherRepository.fetchCurrentWeather(i)
           ) {
               WeatherResult.WeatherError -> {




               }
               is WeatherResult.ServerError -> {
                   //mutableWeatherState.value =
                   //WeatherState.Error(result.errorBody?.message.toString())
               }
               is WeatherResult.Success -> {
                   NotificationCraftman.buildNotification(context,result.data.asNotification())
               }
           }

       }
   }

}
