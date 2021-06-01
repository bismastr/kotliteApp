package com.brillante.kotlite.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreatePsgEntity(
    val distance: Int,
    val longPick: String,
    val fee: Int,
    val longDrop: String,
    val createdAt: String,
    val timeTaken: Int,
    val updatedAt: String,
    val latDrop: String,
    val placeDrop: String,
    val placePick: String,
    val id: Int,
    val time: String,
    val latPick: String,
    val user: Int,
    val status: String,
    val order: Int
) : Parcelable