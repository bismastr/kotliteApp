package com.brillante.kotlite.data.local.entity

import com.google.gson.annotations.SerializedName

data class PassengerListEntity(
    val distance: Int,

    val longPick: String,

    val fee: Int,

    val lastName: String,

    val longDrop: String,

    val timeTaken: Int,

    val phone: String?,

    val latDrop: String,

    val id: Int,

    val time: String,

    val firstName: String,

    val latPick: String,

    val status: String,

    val order: Int
)