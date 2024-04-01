package com.root14.hoopoe.view.binding

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.os.Handler
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.root14.hoopoe.utils.convertPriceFormat

@BindingAdapter("setLivePrice")
fun setLivePrice(textView: TextView, data: String) {
    val previousPrice = textView.text.drop(1).toString().replace(",", "").toDoubleOrNull() ?: 0.0

    if (data.isNotEmpty() and textView.text.isNotEmpty()) {
        val textColorAnimator = ObjectAnimator.ofArgb(
            textView,
            "textColor",
            textView.context.getColor(if (previousPrice < data.toDouble()) android.R.color.holo_green_dark else android.R.color.holo_red_dark),
            textView.context.getColor(android.R.color.black)
        ).apply {
            setEvaluator(ArgbEvaluator())
            duration = 1000
        }

        if (textView.text.isNotEmpty()) {
            textView.postDelayed({
                textView.text = data.convertPriceFormat()
                textColorAnimator.start()
            }, 1000)
        }
    }
}
