package com.example.inviousgchallenge.data.model.ViewStates

import com.google.firebase.auth.FirebaseUser

data class AuthViewState(
    var user: FirebaseUser? = null,
    var success: Boolean? = false,
    var loading: Boolean? = false,
    var error: String? = null
)