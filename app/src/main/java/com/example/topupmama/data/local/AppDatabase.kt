package com.example.topupmama.data.local

import androidx.room.RoomDatabase
import com.example.topupmama.data.local.entities.Country

@androidx.room.Database(
    entities = [
     Country::class
    ],
    version = 1,
    exportSchema = false
)


abstract class AppDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

}