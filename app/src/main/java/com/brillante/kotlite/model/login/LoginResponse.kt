package com.brillante.kotlite.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("status_code")
    var statusCode: Int,

    @SerializedName("access")
    var access: String,
)