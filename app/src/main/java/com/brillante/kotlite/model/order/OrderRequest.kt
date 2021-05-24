package com.brillante.kotlite.model.order

import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @SerializedName( "lat_start")
    var latStart: String,

    @SerializedName("long_start")
    var longStart: String,

    @SerializedName("lat_end")
    var latEnd: String,

    @SerializedName("long_end")
    var longEnd: String,

    @SerializedName("time")
    var time: String,

    @SerializedName("capacity")
    var capacity: Int,

    @SerializedName("car_type")
    var carType: String,
)
