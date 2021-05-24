package com.brillante.kotlite.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class PostOrder(
    @ColumnInfo(name = "latStart")
    var latStart: Long,

    @ColumnInfo(name = "longStart")
    var longStart: Long,

    @ColumnInfo(name = "latEnd")
    var latEnd: Long,

    @ColumnInfo(name = "longEnd")
    var longEnd: Long,

    @ColumnInfo(name = "Time")
    var time: String,

    @ColumnInfo(name = "Capacity")
    var capacity: Int,

    @ColumnInfo(name = "carType")
    var carType: String,
)