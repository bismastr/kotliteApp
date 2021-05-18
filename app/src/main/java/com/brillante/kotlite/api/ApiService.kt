package com.brillante.kotlite.api

import com.brillante.kotlite.BuildConfig
import com.brillante.kotlite.model.direction.DirectionResponses
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val KEY = BuildConfig.API_KEY
    }

    @GET("maps/api/directions/json")
    fun getDirection(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") apiKey: String
    ): Call<DirectionResponses>

}