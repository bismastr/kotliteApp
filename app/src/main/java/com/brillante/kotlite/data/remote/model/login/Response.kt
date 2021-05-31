package com.brillante.kotlite.data.remote.model.login

import com.google.gson.annotations.SerializedName

data class Response(

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
