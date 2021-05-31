package com.brillante.kotlite.data.remote.model.recomendation

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecommendationRequest (
    @SerializedName("lat_pick")
    var latPick: String,
    @SerializedName("long_pick")
    var longPick: String,
    @SerializedName("lat_drop")
    var latDrop: String,
    @SerializedName("long_drop")
    var longDrop: String,
    @SerializedName("time")
    var time: String,
        ) : Parcelable