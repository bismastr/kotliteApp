package com.brillante.kotlite.model.order

import com.google.gson.annotations.SerializedName

data class OrderResponse(

	@field:SerializedName("status_code")
	val statusCode: Int,

)
