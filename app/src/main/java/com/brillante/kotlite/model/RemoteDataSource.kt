package com.brillante.kotlite.model

import android.util.Log
import com.brillante.kotlite.api.ApiConfig
import com.brillante.kotlite.api.ApiService
import com.brillante.kotlite.model.direction.DirectionResponses
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource().apply { instance = this }
            }
    }

    fun getDirection(from: String, to: String, apikey: String, callback: DirectionCallback) {
        var direction: DirectionResponses?
        val client = ApiConfig.getApiServices().getDirection(from, to, apikey)
        client.enqueue(object : Callback<DirectionResponses> {

            override fun onResponse(
                call: Call<DirectionResponses>,
                response: Response<DirectionResponses>
            ) {
                direction = response.body()
                callback.onDirectionReceived(direction)
                Log.d("bisa dong oke", response.message())
            }

            override fun onFailure(call: Call<DirectionResponses>, t: Throwable) {
                Log.e("anjir error", t.toString())
            }
        })
    }

    interface DirectionCallback {
        fun onDirectionReceived(response: DirectionResponses?)
    }
}