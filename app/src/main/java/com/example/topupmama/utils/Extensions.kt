package com.example.topupmama.utils

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager
import com.example.topupmama.R
import com.example.topupmama.data.local.entities.Country


fun Country.getDrawable(type: String) =
    when(type){
        "Sunny" -> R.drawable.sun
        "Partly cloudy" -> R.drawable.partly_cloudy
        "Light rain" ->R.drawable.lightrain
        "Overcast" -> R.drawable.overcast
        "Fog"   ->  R.drawable.fog
        "Mist" -> R.drawable.fog
         "Patchy light rain with thunder" -> R.drawable.thunder
        "Patchy rain possible" -> R.drawable.lightrain
        "Light Snow" -> R.drawable.fog

        else -> R.drawable.sunny
    }



