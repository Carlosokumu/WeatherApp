package com.example.topupmama

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.WorkManager
import com.example.topupmama.base.CountriesBox
import com.example.topupmama.data.local.entities.FavoriteCountry
import com.example.topupmama.di.appModules
import com.example.topupmama.utils.NotificationCraftman
import com.example.topupmama.utils.NotificationHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.error.KoinAppAlreadyStartedException
import org.koin.core.logger.Level
import org.koin.core.module.Module

open class TopUpMama : Application() {


    override fun onCreate() {
        super.onCreate()

        initKoin()
        CountriesBox.init(this)
        WorkManager.getInstance(this).enqueue(NotificationHelper.getNotificationWork())
        createNotificationChannel()


    }




    private fun initKoin() {
        try {
            startKoin {
                androidLogger(Level.ERROR)
                androidContext(applicationContext)
                val modules = mutableListOf<Module>().apply {
                    addAll(appModules)
                }
                modules(modules)
            }
        } catch (error: KoinAppAlreadyStartedException) {
            Log.e("Errors",error.localizedMessage)
        }
    }



    fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name =  "Here"
            val descriptionText = "Here Again"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NotificationCraftman.CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager = getSystemService(
                NOTIFICATION_SERVICE
            ) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }




}