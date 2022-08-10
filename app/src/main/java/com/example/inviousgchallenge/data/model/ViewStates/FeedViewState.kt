package com.example.inviousgchallenge.data.model.ViewStates

import com.example.inviousgchallenge.data.model.Image

data class FeedViewState(
    var loading: Boolean = false,
    var error: String = "",
    var success: Boolean? = false,
    var user: String? = null,
    var imageList: ArrayList<Image>? = arrayListOf(),
)