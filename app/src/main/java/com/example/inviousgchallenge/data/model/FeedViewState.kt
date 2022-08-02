package com.example.inviousgchallenge.data.model

import com.google.firebase.auth.FirebaseUser

data class FeedViewState(
    var loading: Boolean = false,
    var error: String = "",
    var success: Boolean? = false,
    var user: FirebaseUser? = null,
    var signIn: Boolean? = false,
    var imageList: ArrayList<Image>? = arrayListOf(),
) {
}