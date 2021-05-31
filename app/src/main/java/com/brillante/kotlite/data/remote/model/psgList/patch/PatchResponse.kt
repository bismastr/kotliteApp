package com.brillante.kotlite.data.remote.model.psgList.patch

import com.google.gson.annotations.SerializedName

data class PatchResponse(
    @SerializedName("status_code")
    var statusCode: Int
)