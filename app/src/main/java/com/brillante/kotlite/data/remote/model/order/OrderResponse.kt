package com.brillante.kotlite.data.remote.model.order

import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @field:SerializedName("income")
    val income: Int,
    @field:SerializedName("place_start")
    val placeStart: String,
    @field:SerializedName("long_end")
    val longEnd: String,
    @field:SerializedName("lat_start")
    val latStart: String,
    @field:SerializedName("created_at")
    val createdAt: String,
    @field:SerializedName("long_start")
    val longStart: String,
    @field:SerializedName("lat_end")
    val latEnd: String,
    @field:SerializedName("capacity")
    val capacity: Int,
    @field:SerializedName("car_type")
    val carType: String,
    @field:SerializedName("place_end")
    val placeEnd: String,
    @field:SerializedName("updated_at")
    val updatedAt: String,
    @field:SerializedName("total_psg")
    val totalPsg: Int,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("time")
    val time: String,
    @field:SerializedName("user")
    val user: Int,
    @field:SerializedName("status")
    val status: String
)
