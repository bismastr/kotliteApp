

package com.brillante.kotlite.data.remote.model.direction

import com.google.gson.annotations.SerializedName

data class Bounds(
    @SerializedName("northeast")
        var northeast: Northeast?,
    @SerializedName("southwest")
        var southwest: Southwest?
)