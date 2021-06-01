package com.brillante.kotlite.api

import com.brillante.kotlite.data.remote.model.createpsg.CreatePsgRequest
import com.brillante.kotlite.data.remote.model.createpsg.CreatePsgResponse
import com.brillante.kotlite.data.remote.model.detailorder.DetailOrderResponse
import com.brillante.kotlite.data.remote.model.direction.DirectionResponses
import com.brillante.kotlite.data.remote.model.direction.Route
import com.brillante.kotlite.data.remote.model.login.LoginRequest
import com.brillante.kotlite.data.remote.model.login.LoginResponse
import com.brillante.kotlite.data.remote.model.order.OrderRequest
import com.brillante.kotlite.data.remote.model.order.OrderResponse
import com.brillante.kotlite.data.remote.model.psgList.PassengerListResponseItem
import com.brillante.kotlite.data.remote.model.psgList.patch.PatchResponse
import com.brillante.kotlite.data.remote.model.recomendation.RecommendationRequest
import com.brillante.kotlite.data.remote.model.recomendation.RecommendationResponse
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

    //driver
    @POST("/drivers/createorder/")
    fun createOrder(
        @Body request: OrderRequest,
        @Header("Authorization") authHeader: String
    ): Call<OrderResponse>
    @GET("/passengers/pendinglist/{id}/")
    fun getListPsg(@Path("id") orderId: Int, @Header("Authorization") authHeader: String): Call<List<PassengerListResponseItem>>
    @GET("/passengers/list/{id}/")
    fun getAccPsgList(@Path("id") orderId: Int, @Header("Authorization") authHeader: String): Call<List<PassengerListResponseItem>>
    //TODO arriving
    @PATCH("/passengers/accepted/{id}/")
    fun patchPsgAcc(@Path("id") id: Int): Call<PatchResponse>
    @GET("/drivers/route/{idOrder}/")
    fun getOnGoingRoute(@Path("idOrder") orderId: Int, @Header("Authorization") authHeader: String): Call<List<Route>>
    @GET ("/drivers/detail/{idOrder}/")
    fun getDetailOrder(@Path ("idOrder") orderId: Int, @Header("Authorization") authHeader: String): Call<DetailOrderResponse>

    //passenger
    @POST("/drivers/recommendationlist/")
    fun postRecommendation(
        @Body request: RecommendationRequest,
        @Header("Authorization") authHeader: String
    ): Call<RecommendationResponse>

    @POST("/passengers/createpsg/{id}/")
    fun createPsg(
        @Body request: CreatePsgRequest,
        @Header("Authorization") authHeader: String,
        @Path("id") id: Int
    ): Call<CreatePsgResponse>

    //patch status passenger
    @PATCH("/passengers/arrived/{id}/")
    fun patchPsgArrived(@Path("id") id: Int, @Header("Authorization") authHeader: String): Call<PatchResponse>

    @PATCH("/passengers/startride/{id}/")
    fun patchPsgStartRide(@Path("id") id: Int, @Header("Authorization") authHeader: String): Call<PatchResponse>

    @PATCH("/passengers/completeride/{id}/")
    fun patchPsgCompleteRide(@Path("id") id: Int, @Header("Authorization") authHeader: String): Call<PatchResponse>

    @PATCH("/passengers/done/{id}/")
    fun patchPsgDone(@Path("id") id: Int, @Header("Authorization") authHeader: String): Call<PatchResponse>


}