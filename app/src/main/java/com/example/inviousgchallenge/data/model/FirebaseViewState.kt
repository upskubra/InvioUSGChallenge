package com.example.inviousgchallenge.data.model

import android.net.Uri
import com.google.firebase.auth.FirebaseUser

data class FirebaseViewState(
    var isLoading: Boolean = false,
    var error: String = "",
    var isSuccess: Boolean = false,
    var uri: Uri? = null,
    var signIn: Boolean = false,
    var user: FirebaseUser? = null,
    var feedImageList: ArrayList<Image>? = arrayListOf()
)