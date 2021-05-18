package com.brillante.kotlite.network

import com.brillante.kotlite.model.LoginRequest
import com.brillante.kotlite.ui.responses.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiEndPoint {

    @POST("/users/login/")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}