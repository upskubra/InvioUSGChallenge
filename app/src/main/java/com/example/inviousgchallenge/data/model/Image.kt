package com.example.inviousgchallenge.data.model

import android.net.Uri


data class Image(
    // temp variable
    val uri: Uri?,
    val width: Int?,
    val height: Int?
) {
    override fun toString(): String {
        return "Image(uri=$uri, width=$width, height=$height)"
    }
}