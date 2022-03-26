package com.example.topupmama.data.models

data class Location(
    var name: String,
    var country: String,
    var lat: Double,
    var lon: Double,
    var tz_id: String,
    var localtime_epoch: Int,
    var localtime: String
)