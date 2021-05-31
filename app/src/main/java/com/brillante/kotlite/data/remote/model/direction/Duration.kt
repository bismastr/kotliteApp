

package com.brillante.kotlite.data.remote.model.direction

import com.google.gson.annotations.SerializedName

data class Duration(
        @SerializedName("text")
        var text: String?,
        @SerializedName("value")
        var value: Int?
)