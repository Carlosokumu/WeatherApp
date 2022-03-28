package com.example.topupmama.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.topupmama.R
import com.example.topupmama.base.CountriesBox
import com.example.topupmama.data.local.entities.Country
import com.example.topupmama.data.local.entities.FavoriteCountry
import java.net.URL
import java.util.*


object NotificationCraftman {

    const val CHANNEL_ID = "2001"

    fun buildNotification(context: Context, temp: String?) {
        ///CountriesBox.store.boxFor(FavoriteCountry::class.java).all
        val allnotifications = CountriesBox.store.boxFor(FavoriteCountry::class.java).all
        if (allnotifications.isNotEmpty()) {
            for (i in allnotifications.indices) {
                if (temp != null) {
                    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_logo)
                        .setContentTitle(allnotifications[i].cityName)
                        .setContentText("Current Temperature is $temp °C")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                    val m = NotificationManagerCompat.from(context)
                    m.notify(i, builder.build())
                }

            }
        }


    }

    fun buildNotificationTwo(context: Context, country: Country?) {
        val allnotifications = CountriesBox.store.boxFor(FavoriteCountry::class.java).all
        if (allnotifications.isNotEmpty()) {
            if (country != null) {
                val url = URL("https:".plus(country.icon))
                val image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_logo)
                    .setContentTitle(country.cityName)
                    .setStyle(
                        NotificationCompat.BigPictureStyle()
                            .bigPicture(image)
                    )
                    .setContentText("Temperature is ${country.temp.toString()} °C with ${country.description} weather")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                val m = NotificationManagerCompat.from(context)
                m.notify((0..10).random(), builder.build())
            }
        }
    }
}
