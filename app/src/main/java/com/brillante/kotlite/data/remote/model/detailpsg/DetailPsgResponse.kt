package com.brillante.kotlite.data.remote.model.detailpsg

import com.google.gson.annotations.SerializedName

data class DetailPsgResponse(

	@field:SerializedName("driver_id")
	val driverId: Int,

	@field:SerializedName("distance")
	val distance: Int,

	@field:SerializedName("long_pick")
	val longPick: String,

	@field:SerializedName("fee")
	val fee: Int,

	@field:SerializedName("last_name")
	val lastName: String,

	@field:SerializedName("long_drop")
	val longDrop: String,

	@field:SerializedName("time_taken")
	val timeTaken: Int,

	@field:SerializedName("phone")
	val phone: String,

	@field:SerializedName("lat_drop")
	val latDrop: String,

	@field:SerializedName("place_drop")
	val placeDrop: String,

	@field:SerializedName("place_pick")
	val placePick: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("time")
	val time: String,

	@field:SerializedName("user")
	val user: Int,

	@field:SerializedName("first_name")
	val firstName: String,

	@field:SerializedName("lat_pick")
	val latPick: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("order")
	val order: Int
)
