package com.example.inviousgchallenge.data.model.ViewStates

import android.net.Uri

data class DeleteViewState(
    var isDeleted: Boolean? = false,
    var error: String? = null,
    var loading: Boolean? = false,
    var user: String? = null,
    var imageId: String? = null,
) {
}