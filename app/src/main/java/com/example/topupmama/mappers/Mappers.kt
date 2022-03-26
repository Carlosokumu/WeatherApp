package com.example.topupmama.mappers

import com.example.topupmama.data.local.entities.Country
import com.example.topupmama.data.models.WeatherResponse


fun WeatherResponse.asCountry(country: Country) = Country(
    countryName =  this.location.country,
    cityName = this.location.name,
    isFavorite = country.isFavorite,
    temp = this.current.temp_c,
    priority = country.priority,
    icon = this.current.condition.icon,
    precipitation = this.current.precip_in,
    description = this.current.condition.text,
    feelslike = this.current.feelslike_c,
    windspeed = this.current.wind_mph,
    updatedAt = this.current.last_updated

)


fun WeatherResponse.asNotification() =
    Country(
        countryName =  this.location.country,
        cityName = this.location.name,
        isFavorite = true,
        temp = this.current.temp_c,
        priority = -1,
        icon = this.current.condition.icon,
        precipitation = this.current.precip_in,
        description = this.current.condition.text,
        feelslike = this.current.feelslike_c,
        windspeed = this.current.windKph,
        updatedAt = this.current.last_updated)


