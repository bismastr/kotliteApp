

package com.brillante.kotlite.data.remote.model.direction

import com.google.gson.annotations.SerializedName

data class Polyline(
        @SerializedName("points")
        var points: String?
)