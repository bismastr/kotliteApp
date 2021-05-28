package com.brillante.kotlite.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.brillante.kotlite.api.ApiConfig
import com.brillante.kotlite.model.direction.DirectionResponses
import com.brillante.kotlite.model.login.LoginRequest
import com.brillante.kotlite.model.login.LoginResponse
import com.brillante.kotlite.model.order.OrderRequest
import com.brillante.kotlite.model.psgList.PassengerListResponse
import com.brillante.kotlite.model.psgList.PassengerListResponseItem
import com.brillante.kotlite.model.psgList.patch.PatchResponse
import com.brillante.kotlite.preferences.SessionManager
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

    fun loginUser(
        username: String,
        password: String,
        sessionManager: SessionManager,
        context: Context,
        callback: LoginCallback
    ) {
        val client = ApiConfig.getWebServices().login(LoginRequest(username, password))
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { sessionManager.saveAuthToken(it.access) }
                    callback.onLoginReceived(true)

                } else callback.onLoginReceived(false)
                Log.d("LOGIN", response.code().toString())

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback.onLoginReceived(false)
                Toast.makeText(
                    context,
                    "Something Went Wrong",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    fun createOrder(
        latStart: String,
        longStart: String,
        latEnd: String,
        longEnd: String,
        time: String,
        capacity: Int,
        carType: String,
        context: Context,
        callback: OrderCallback,
        authHeader: String
    ) {
        val client = ApiConfig.getWebServices().createOrder(OrderRequest(latStart, longStart, latEnd, longEnd, time, capacity, carType), authHeader)
        client.enqueue(object : Callback<OrderRequest> {
            override fun onResponse(call: Call<OrderRequest>, response: Response<OrderRequest>) {
                Log.d("AUTH", authHeader.toString())
                if (response.isSuccessful){
                    callback.onOrderReceived(true)
                    Toast.makeText(
                        context,
                        "Order Success",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        response.code().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("ORDER", response.code().toString())
                }

            }

            override fun onFailure(call: Call<OrderRequest>, t: Throwable) {
                callback.onOrderReceived(false)
                Toast.makeText(
                    context,
                    "Order Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    fun getPassengerList(callback: PassengerListCallback){
        val client = ApiConfig.getWebServices().getListPsg()
        client.enqueue(object: Callback<List<PassengerListResponseItem>> {
            override fun onResponse(
                call: Call<List<PassengerListResponseItem>>,
                response: Response<List<PassengerListResponseItem>>
            ) {
                if (response.isSuccessful){
                    val data = response.body()
                    if (data != null) {
                        callback.onPsgListReceived(data)
                    } else Log.d("GET PSG", "DATA NULL")
                } else Log.d("GET PSG", response.code().toString())
            }

            override fun onFailure(call: Call<List<PassengerListResponseItem>>, t: Throwable) {
                Log.d("GET PSG", t.toString())
            }


        })
    }

    fun getAccPsgList(callback: PsgAccListCallback){
        val client = ApiConfig.getWebServices().getAccPsgList()
        client.enqueue(object : Callback<List<PassengerListResponseItem>> {
            override fun onResponse(
                call: Call<List<PassengerListResponseItem>>,
                response: Response<List<PassengerListResponseItem>>
            ) {
                if (response.isSuccessful){
                    val data = response.body()
                    if (data != null) {
                        callback.onPsgListReceived(data)
                    } else Log.d("GET PSG", "DATA NULL")
                } else Log.d("GET PSG", response.code().toString())
            }

            override fun onFailure(call: Call<List<PassengerListResponseItem>>, t: Throwable) {
                Log.d("GET PSG", t.toString())
            }

        })
    }

    fun patchPsgAcc(id: Int, callback: PsgAccPatchCallback){
        val client = ApiConfig.getWebServices().patchPsgAcc(id)
        client.enqueue(object : Callback<PatchResponse>{
            override fun onResponse(call: Call<PatchResponse>, response: Response<PatchResponse>) {
                if (response.isSuccessful){
                    Log.d("PATCH ACC", "SUCCESS")
                    callback.onPsgAccReceived(true)
                } else {
                    callback.onPsgAccReceived(true)
                    Log.d("PATCH ACC", response.code().toString())
                }
            }

            override fun onFailure(call: Call<PatchResponse>, t: Throwable) {
                callback.onPsgAccReceived(true)
                Log.d("PATCH ACC", t.toString())
            }

        })
    }

    fun patchPsgArrived(id: Int, callback: PsgAccPatchCallback){
        val client = ApiConfig.getWebServices().patchPsgArrived(id)
        client.enqueue(object : Callback<PatchResponse>{
            override fun onResponse(call: Call<PatchResponse>, response: Response<PatchResponse>) {
                if (response.isSuccessful){
                    Log.d("PATCH ARRIVED", "SUCCESS")
                    callback.onPsgAccReceived(true)
                } else {
                    callback.onPsgAccReceived(true)
                    Log.d("PATCH ARRIVED", response.code().toString())
                }
            }

            override fun onFailure(call: Call<PatchResponse>, t: Throwable) {
                callback.onPsgAccReceived(true)
                Log.d("PATCH ARRIVED", t.toString())
            }

        })
    }

    fun patchPsgStartRide(id: Int, callback: PsgAccPatchCallback){
        val client = ApiConfig.getWebServices().patchPsgStartRide(id)
        client.enqueue(object : Callback<PatchResponse>{
            override fun onResponse(call: Call<PatchResponse>, response: Response<PatchResponse>) {
                if (response.isSuccessful){
                    Log.d("PATCH START", "SUCCESS")
                    callback.onPsgAccReceived(true)
                } else {
                    callback.onPsgAccReceived(true)
                    Log.d("PATCH START", response.code().toString())
                }
            }

            override fun onFailure(call: Call<PatchResponse>, t: Throwable) {
                callback.onPsgAccReceived(true)
                Log.d("PATCH START", t.toString())
            }

        })
    }

    fun patchPsgCompleteRide(id: Int, callback: PsgAccPatchCallback){
        val client = ApiConfig.getWebServices().patchPsgCompleteRide(id)
        client.enqueue(object : Callback<PatchResponse>{
            override fun onResponse(call: Call<PatchResponse>, response: Response<PatchResponse>) {
                if (response.isSuccessful){
                    Log.d("PATCH COMPLETE", "SUCCESS")
                    callback.onPsgAccReceived(true)
                } else {
                    callback.onPsgAccReceived(true)
                    Log.d("PATCH COMPLETE", response.code().toString())
                }
            }

            override fun onFailure(call: Call<PatchResponse>, t: Throwable) {
                callback.onPsgAccReceived(true)
                Log.d("PATCH COMPLETE", t.toString())
            }

        })
    }

    fun patchPsgDone(id: Int, callback: PsgAccPatchCallback){
        val client = ApiConfig.getWebServices().patchPsgDone(id)
        client.enqueue(object : Callback<PatchResponse>{
            override fun onResponse(call: Call<PatchResponse>, response: Response<PatchResponse>) {
                if (response.isSuccessful){
                    Log.d("PATCH DONE", "SUCCESS")
                    callback.onPsgAccReceived(true)
                } else {
                    callback.onPsgAccReceived(true)
                    Log.d("PATCH DONE", response.code().toString())
                }
            }

            override fun onFailure(call: Call<PatchResponse>, t: Throwable) {
                callback.onPsgAccReceived(true)
                Log.d("PATCH DONE", t.toString())
            }

        })
    }



    interface PsgAccPatchCallback{
        fun onPsgAccReceived(isSuccess: Boolean)
    }

    interface PsgAccListCallback{
        fun onPsgListReceived(response: List<PassengerListResponseItem>)
    }

    interface  PassengerListCallback {
        fun onPsgListReceived(response: List<PassengerListResponseItem>)
    }
    interface DirectionCallback {
        fun onDirectionReceived(response: DirectionResponses?)
    }

    interface LoginCallback {
        fun onLoginReceived(isSucces: Boolean)
    }

    interface OrderCallback {
        fun onOrderReceived(isSucces: Boolean)
    }
}