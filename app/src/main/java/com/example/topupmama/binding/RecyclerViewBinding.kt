package com.example.topupmama.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topupmama.view.CustomLinearLayout


object RecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("adapter")
    fun bindAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        view.adapter = adapter
        view.layoutManager = CustomLinearLayout(view.context, LinearLayoutManager.VERTICAL,false)
        //view.recycledViewPool.clear()
        //view.validateV
    }
}