package com.brillante.kotlite.data.remote.model.createpsg

import com.google.gson.annotations.SerializedName

data class CreatePsgRequest(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("lat_pick")
    val latPick: String,
    @SerializedName("long_pick")
    val longPick: String,
    @SerializedName("place_pick")
    val place_pick: String,
    @SerializedName("lat_drop")
    val latDrop: String,
    @SerializedName("long_drop")
    val longDrop: String,
    @SerializedName("place_drop")
    val placeDrop: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("time_taken")
    val time_taken: Int,
    @SerializedName("fee")
    val fee: Int,
)