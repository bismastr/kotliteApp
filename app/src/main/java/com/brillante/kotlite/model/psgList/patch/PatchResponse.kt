package com.brillante.kotlite.model.psgList.patch

import com.google.gson.annotations.SerializedName

data class PatchResponse(
    @SerializedName("status_code")
    var statusCode: Int
)