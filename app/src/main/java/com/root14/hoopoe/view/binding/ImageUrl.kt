package com.root14.hoopoe.view.binding

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.root14.hoopoe.utils.Url

@BindingAdapter("bindIcon")
fun bindIcon(imgView: ImageView, symbol: String) {
    symbol.let {
        val symbolUri =
            Url.getIconUrl(symbol.lowercase()).toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context).load(symbolUri).into(imgView)
    }
}
