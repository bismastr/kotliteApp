package com.brillante.kotlite.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    private const val BASE_URL = "https://proud-lamp-312513.et.r.appspot.com"
    private val retrofitConnect: Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val apiRequest: ApiEndPoint = retrofitConnect.create(ApiEndPoint::class.java)
}