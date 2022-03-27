package com.example.topupmama.data.models


data class ForecastDay(
    var forecastday: List<Days>
)

class Days(
    var date: String,
    var day: Day,
)

class Day(
   var avgtemp_c: Double
)







