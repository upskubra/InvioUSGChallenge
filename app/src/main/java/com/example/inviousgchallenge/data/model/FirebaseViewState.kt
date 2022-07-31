package com.example.inviousgchallenge.data.model

import android.net.Uri

data class FirebaseViewState(
    var isLoading: Boolean = false,
    var error: String = "",
    var isSuccess: Boolean = false,
    var uri: Uri? = null,
    var feedImageList: ArrayList<Image>? = arrayListOf()
)