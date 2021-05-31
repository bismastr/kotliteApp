package com.brillante.kotlite.data.local.entity

data class CreatePsgEntity (
    val id: Int,
    val time: String,
    val status: String,
    val carType: String,
    val totalPsg: Int,
    val placeStart: String,
    val placeEnd: String,
    val capacity: Int
        )