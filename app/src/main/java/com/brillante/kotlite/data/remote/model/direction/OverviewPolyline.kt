

package com.brillante.kotlite.data.remote.model.direction

import com.google.gson.annotations.SerializedName

data class OverviewPolyline(
        @SerializedName("points")
        var points: String?
)