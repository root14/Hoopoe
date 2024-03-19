package com.root14.hoopoe.view.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("selectedIcon")
fun selectedIcon(imageView: ImageView, selected: Boolean) {
    if (selected) {
        imageView.visibility = View.VISIBLE
    } else {
        imageView.visibility = View.GONE
    }
}