package com.brillante.kotlite.data.remote.model.createpsg

import com.google.gson.annotations.SerializedName

data class CreatePsgRequest(
    @field:SerializedName("distance")
    val distance: Int,

    @field:SerializedName("long_pick")
    val longPick: Double,

    @field:SerializedName("minimum_fee")
    val minimumFee: Double,

    @field:SerializedName("long_drop")
    val longDrop: Double,

    @field:SerializedName("time_taken")
    val timeTaken: Int,

    @field:SerializedName("lat_drop")
    val latDrop: Double,

    @field:SerializedName("place_drop")
    val placeDrop: String,

    @field:SerializedName("maximum_fee")
    val maximumFee: Int,

    @field:SerializedName("place_pick")
    val placePick: String,

    @field:SerializedName("time")
    val time: String,

    @field:SerializedName("user")
    val user: Int,

    @field:SerializedName("lat_pick")
    val latPick: Double,

    @field:SerializedName("status")
    val status: String
)