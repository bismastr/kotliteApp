

package com.brillante.kotlite.data.remote.model.direction

import com.google.gson.annotations.SerializedName

data class GeocodedWaypoint(
        @SerializedName("geocoder_status")
        var geocoderStatus: String?,
        @SerializedName("place_id")
        var placeId: String?,
        @SerializedName("types")
        var types: List<String?>?
)