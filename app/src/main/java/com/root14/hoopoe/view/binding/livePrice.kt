package com.root14.hoopoe.view.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.root14.hoopoe.utils.convertPriceFormat

@BindingAdapter("setLivePrice")
fun setLivePrice(textView: TextView, data: String) {
    if (data.isNotEmpty()) {
        textView.text = data.convertPriceFormat()
    }
}