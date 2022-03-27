package com.example.topupmama.data.models

import com.example.topupmama.data.local.entities.Country

sealed class ForeCastState {



    object Loading :  ForeCastState()


    data class Result(val weather:  ForeCastResponse) : ForeCastState()


    data class Error(val message: String) : ForeCastState()
}