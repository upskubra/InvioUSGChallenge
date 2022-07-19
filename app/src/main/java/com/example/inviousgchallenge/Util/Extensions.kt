package com.example.inviousgchallenge.Util

import android.view.View

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