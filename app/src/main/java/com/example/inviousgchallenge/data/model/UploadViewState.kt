package com.example.inviousgchallenge.data.model

import android.net.Uri

data class UploadViewState(
    var loading: Boolean = false,
    var error: String = "",
    var success: Boolean? = false,
    var uri: Uri? = null,
    var uriList: List<Uri>? = null,
)