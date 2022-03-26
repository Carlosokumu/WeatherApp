package com.example.topupmama.binding

import android.view.View
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.topupmama.R



@BindingAdapter("onBackPressed")
fun bindOnBackPressed(view: View, onBackPress: Boolean) {
    val context = view.context
    if (onBackPress && context is OnBackPressedDispatcherOwner) {
        view.setOnClickListener {
            context.onBackPressedDispatcher.onBackPressed()
        }
    }
}



@BindingAdapter("gone")
fun bindGone(view: View, shouldBeGone: Boolean) {
    view.visibility = if (shouldBeGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}