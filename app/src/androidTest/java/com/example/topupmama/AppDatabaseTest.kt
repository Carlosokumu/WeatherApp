package com.example.topupmama

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.topupmama.data.local.AppDatabase
import com.example.topupmama.data.local.WeatherDao
import com.example.topupmama.data.local.entities.Country
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith


/*
      This Test class basically tests:
      Room
      Daos
 */

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest : TestCase() {

    private lateinit var appDatabase: AppDatabase
    private lateinit var dao: WeatherDao


    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).createFromAsset("database/countries.db").build()
        dao = appDatabase.weatherDao()
    }


    @After
    fun closeDb() {
         appDatabase.close()
    }




    /*

         Check if Dao and room works correctly
     */


    @Test
    fun insertWeatherAndFetch(): Unit = runBlocking {
        val country = Country(
            countryName = "Nigeria",
            cityName = "Lagos",
            isFavorite = false,
            icon = null,
            priority = 0,
            feelslike = null,
            description = "Sunny",
            updatedAt = null,
            temp = null,
            precipitation = 0.2,
            windspeed = 0.0
        )
        dao.insertCountry(country)
        Truth.assertThat(dao.getAllCountries().contains(country))
    }


    /*
        Check number of cities in the Db[should be 20]
     */
    @Test
    fun getCountriesNumber(){
        runBlocking {
            val size = dao.getAllCountries().size
            Truth.assertThat(size == 20)
        }
    }



   @Test
   fun isCityMadeFavorite(){
       runBlocking {
           dao.update(true, -1,"Mombasa")
       }
   }



}