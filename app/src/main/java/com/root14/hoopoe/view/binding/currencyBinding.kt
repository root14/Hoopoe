package com.root14.hoopoe.view.binding

import android.graphics.Color
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.root14.hoopoe.R
import com.root14.hoopoe.utils.convertMarketCap
import com.root14.hoopoe.utils.convertPriceChange
import com.root14.hoopoe.utils.convertPriceFormat

@BindingAdapter("setCurrency")
fun setCurrency(textView: TextView, price: String) {
    price.let {
        textView.text = price.convertPriceFormat()
    }
}

@BindingAdapter("setMarketCap")
fun setMarketCap(textView: TextView, marketCap: String) {
    marketCap.let {
        textView.text = marketCap.convertMarketCap()
    }
}

@BindingAdapter("setPriceChange")
fun setPriceChange(textView: TextView, priceChange: String) {
    priceChange.let {
        textView.text = priceChange.convertPriceChange()

        val baseView = textView.rootView.resources
        if (priceChange.isNotEmpty()) {
            val price = priceChange.toDouble()
            if (price > 0) {
                textView.setTextColor(baseView.getColor(R.color.positiveColor))
            } else {
                textView.setTextColor(baseView.getColor(R.color.negativeColor))
            }
        }
    }
}