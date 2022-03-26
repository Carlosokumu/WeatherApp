package com.example.topupmama.data.local.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id



/*
    Entity class to help us maintain the favorite countries
 */

@Entity
data class FavoriteCountry(
    @Id
    var id: Long = 0,
    var countryName: String,
    var cityName: String
)