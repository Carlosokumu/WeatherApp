package com.example.topupmama.base

import android.content.Context
import com.example.topupmama.data.local.entities.MyObjectBox
import io.objectbox.BoxStore

object CountriesBox {
    lateinit var store: BoxStore
        private set

    fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }
}