package com.example.topupmama.data.models




data class ForeCastResponse(
    var location: Location,
    var current: Current,
    var forecast: ForecastDay
)
