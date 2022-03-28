package com.example.topupmama.utils

import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkRequest
import com.example.topupmama.base.CountriesBox
import com.example.topupmama.data.local.entities.FavoriteCountry
import com.example.topupmama.work.NotifyWorker
import java.util.concurrent.TimeUnit


object NotificationHelper {


    fun getNotificationWork(): WorkRequest {
        val favoriteCountries = CountriesBox.store.boxFor(FavoriteCountry::class.java).all
        val cityNames = mutableListOf<String>()

        if (favoriteCountries.isNotEmpty()) {
            for (i in favoriteCountries) {
                cityNames.add(i.cityName)
            }
        }
        val inputData = Data.Builder()
            .putStringArray(
                "CITIES",
                *cityNames.toTypedArray()
            )
            .build()


        return PeriodicWorkRequest.Builder(
            NotifyWorker::class.java, 1, // repeating interval
            TimeUnit.HOURS,
        )
            .setInputData(inputData)
            .build()
    }


}





