package com.example.inviousgchallenge.Util

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

inline fun View.setOnDoubleClickListener(
    // https://blog.mindorks.com/understanding-inline-noinline-and-crossinline-in-kotlin
    crossinline onDoubleClick: (View) -> Unit
) {
    setOnClickListener(object : DoubleClickListener() {
        override fun onDoubleClick(v: View) {
            onDoubleClick(v)
        }
    })
}

fun AppCompatImageView.loadUrl(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}