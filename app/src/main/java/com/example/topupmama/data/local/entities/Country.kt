package com.example.topupmama.data.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(indices = [Index(value = ["cityName"], unique = true)])
data class Country(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val countryName: String,
    val cityName: String,
    var isFavorite: Boolean,
    val temp: Double?,
    var priority: Int,
    val icon: String?,
    val feelslike: Double?,
    val windspeed: Double?,
    val precipitation: Double?,
    val description: String?,
    val updatedAt: String?
):Parcelable{


    @JvmName("isFavoriteUser")
    fun isFavorite() = isFavorite
}
