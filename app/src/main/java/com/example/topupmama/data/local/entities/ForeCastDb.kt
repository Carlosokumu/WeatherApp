package com.example.topupmama.data.local.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data  class ForeCastDb(
    @Id
    var id: Long = 0,
    var cityName: String,
    var temps: MutableList<String>
)
