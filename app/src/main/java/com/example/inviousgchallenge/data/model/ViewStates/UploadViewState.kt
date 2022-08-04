package com.example.inviousgchallenge.data.model.ViewStates

import android.net.Uri

data class UploadViewState(
    var loading: Boolean = false,
    var error: String = "",
    var success: Boolean? = false,
    var uri: Uri? = null,
    var user: String? = null,
    var description: String? = "",
    var uriList: List<Uri>? = null,
)