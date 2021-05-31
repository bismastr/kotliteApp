package com.brillante.kotlite.data.local.entity

data class RecommendationEntity(
    val psgData: PsgDataEntity,
    val recommendations: List<RecommendationsItemEntity>
)

data class PsgDataEntity(
    val distance: Int,
    val longPick: Double,
    val minimumFee: Double,
    val longDrop: Double,
    val timeTaken: Int,
    val latDrop: Double,
    val placeDrop: String,
    val maximumFee: Int,
    val placePick: String,
    val time: String,
    val user: Int,
    val latPick: Double,
    val status: String
)

data class RecommendationsItemEntity(
    val income: Int,
    val placeStart: String,
    val longEnd: String,
    val latStart: String,
    val lastName: String,
    val longStart: String,
    val latEnd: String,
    val capacity: Int,
    val carType: String,
    val placeEnd: String,
    val totalPsg: Int,
    val phone: Any,
    val id: Int,
    val time: String,
    val firstName: String,
    val user: Int,
    val status: String
)