package com.brillante.kotlite.data.remote.model.recomendation

import com.google.gson.annotations.SerializedName

data class RecommendationResponse(

	@field:SerializedName("psg_data")
	val psgData: PsgData,

	@field:SerializedName("recommendations")
	val recommendations: List<RecommendationsItem>
)

data class PsgData(

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

data class RecommendationsItem(

	@field:SerializedName("income")
	val income: Int,

	@field:SerializedName("place_start")
	val placeStart: String,

	@field:SerializedName("long_end")
	val longEnd: String,

	@field:SerializedName("lat_start")
	val latStart: String,

	@field:SerializedName("last_name")
	val lastName: String,

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

	@field:SerializedName("total_psg")
	val totalPsg: Int,

	@field:SerializedName("phone")
	val phone: Any = "08",

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("time")
	val time: String,

	@field:SerializedName("first_name")
	val firstName: String,

	@field:SerializedName("user")
	val user: Int,

	@field:SerializedName("status")
	val status: String
)
