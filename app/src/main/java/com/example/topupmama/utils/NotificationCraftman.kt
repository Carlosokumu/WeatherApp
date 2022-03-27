package com.example.topupmama.utils

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.topupmama.R
import com.example.topupmama.base.CountriesBox
import com.example.topupmama.data.local.entities.Country
import com.example.topupmama.data.local.entities.FavoriteCountry


object NotificationCraftman {

    const val CHANNEL_ID = "2001"
    private const val REMINDER_ID = 21

    fun buildNotification(context: Context, country: Country) {
        ///CountriesBox.store.boxFor(FavoriteCountry::class.java).all
        val allnotifications = CountriesBox.store.boxFor(FavoriteCountry::class.java).all
        if (allnotifications.isNotEmpty()) {
            for (i in 0..allnotifications.size) {
                val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_rain)
                    .setContentTitle(country.cityName)
                    .setContentText("Current Temperature is ${country.temp} °C")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                val m = NotificationManagerCompat.from(context)
                m.notify(i, builder.build())
            }
        }


    }
}
