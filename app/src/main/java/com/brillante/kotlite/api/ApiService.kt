package com.brillante.kotlite.api

import com.brillante.kotlite.model.login.LoginRequest
import com.brillante.kotlite.model.direction.DirectionResponses
import com.brillante.kotlite.model.login.LoginResponse
import com.brillante.kotlite.model.order.OrderRequest
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("maps/api/directions/json")
    fun getDirection(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") apiKey: String
    ): Call<DirectionResponses>

    @POST("/users/login/")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/drivers/createorder/")
    fun createOrder(@Body request: OrderRequest, @Header("Authorization") authHeader: String): Call<OrderRequest>

}