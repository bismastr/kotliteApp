package com.brillante.kotlite.api

import com.brillante.kotlite.model.direction.DirectionResponses
import com.brillante.kotlite.model.login.LoginRequest
import com.brillante.kotlite.model.login.LoginResponse
import com.brillante.kotlite.model.order.OrderRequest
import com.brillante.kotlite.model.psgList.PassengerListResponseItem
import com.brillante.kotlite.model.psgList.patch.PatchResponse
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

    @GET("/passengers/pendinglist/13/")
    fun getListPsg(): Call<List<PassengerListResponseItem>>

    @GET("/passengers/list/13/")
    fun getAccPsgList(): Call<List<PassengerListResponseItem>>

    @PATCH ("/passengers/accepted/{id}/")
    fun patchPsgAcc(@Path("id") id: Int): Call<PatchResponse>

    //patch status passenger
    @PATCH("/passengers/arrived/{id}/")
    fun patchPsgArrived(@Path("id") id: Int): Call<PatchResponse>
    @PATCH("/passengers/startride/{id}/")
    fun patchPsgStartRide(@Path("id") id: Int): Call<PatchResponse>
    @PATCH("/passengers/completeride/{id}/")
    fun patchPsgCompleteRide(@Path("id")id: Int): Call<PatchResponse>
    @PATCH("/passengers/done/{id}/")
    fun patchPsgDone(@Path("id")id: Int): Call<PatchResponse>


}