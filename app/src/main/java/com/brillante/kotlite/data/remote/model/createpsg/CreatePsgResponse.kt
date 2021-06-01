package com.brillante.kotlite.data.remote.model.createpsg

import com.google.gson.annotations.SerializedName

data class CreatePsgResponse(

	@field:SerializedName("distance")
	val distance: Int,

	@field:SerializedName("long_pick")
	val longPick: String,

	@field:SerializedName("fee")
	val fee: Int,

	@field:SerializedName("long_drop")
	val longDrop: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("time_taken")
	val timeTaken: Int,

	@field:SerializedName("updated_at")
	val updatedAt: String,

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

	@field:SerializedName("lat_pick")
	val latPick: String,

	@field:SerializedName("user")
	val user: Int,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("order")
	val order: Int
)
