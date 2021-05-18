package com.brillante.kotlite.api

import com.brillante.kotlite.model.login.LoginRequest
import com.brillante.kotlite.model.direction.DirectionResponses
import com.brillante.kotlite.model.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("maps/api/directions/json")
    fun getDirection(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") apiKey: String
    ): Call<DirectionResponses>

    @POST("/users/login/")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

}