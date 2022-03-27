package com.example.topupmama.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView


object RecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("adapter")
    fun bindAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        view.adapter = adapter
        view.recycledViewPool.clear()
    }
}