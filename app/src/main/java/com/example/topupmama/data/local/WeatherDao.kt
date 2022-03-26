package com.example.topupmama.data.local

import androidx.room.*
import com.example.topupmama.data.local.entities.Country

@Dao
interface WeatherDao {


    /*
     *
         * Fetch all countries from the Db
     *
     */

    @Query("SELECT * FROM Country ORDER BY priority")
    suspend fun getAllCountries(): List<Country>



    /*
    *
        * Insert countries into the Db for offline use
    *
    */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countyList: List<Country>)

    /*
    *
        * Insert countries into the Db for offline use
    *
    */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(county: Country)


    /*
       *
         * Updating country data
       *
     */

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(county: Country)


    /*
        * Update the priority and favorite fields
     */
    @Query("UPDATE Country SET isFavorite = :isFavorite, priority = :priority WHERE cityName =:cityName")
    suspend fun update(isFavorite: Boolean,priority: Int?, cityName: String)

}