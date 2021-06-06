package com.brillante.kotlite.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.brillante.kotlite.api.ApiConfig
import com.brillante.kotlite.data.remote.model.createpsg.CreatePsgRequest
import com.brillante.kotlite.data.remote.model.createpsg.CreatePsgResponse
import com.brillante.kotlite.data.remote.model.detailorder.DetailOrderResponse
import com.brillante.kotlite.data.remote.model.detailpsg.DetailPsgResponse
import com.brillante.kotlite.data.remote.model.direction.DirectionResponses
import com.brillante.kotlite.data.remote.model.direction.Route
import com.brillante.kotlite.data.remote.model.login.LoginRequest
import com.brillante.kotlite.data.remote.model.login.LoginResponse
import com.brillante.kotlite.data.remote.model.order.OrderRequest
import com.brillante.kotlite.data.remote.model.order.OrderResponse
import com.brillante.kotlite.data.remote.model.psgList.PassengerListResponseItem
import com.brillante.kotlite.data.remote.model.psgList.patch.PatchResponse
import com.brillante.kotlite.data.remote.model.recomendation.PsgData
import com.brillante.kotlite.data.remote.model.recomendation.RecommendationRequest
import com.brillante.kotlite.data.remote.model.recomendation.RecommendationResponse
import com.brillante.kotlite.data.remote.model.recomendation.RecommendationsItem
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
        val client = ApiConfig.getWebServices().createOrder(
            OrderRequest(
                latStart,
                longStart,
                latEnd,
                longEnd,
                time,
                capacity,
                carType
            ), authHeader
        )

        client.enqueue(object : Callback<OrderResponse> {
            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        callback.onOrderReceived(data)
                    } else Log.d("ORDER", "NULL")
                } else {
                    Log.d("ORDER", response.code().toString())
                }
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Order Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    fun getPassengerList(callback: PassengerListCallback, orderId: Int, authHeader: String) {
        val client = ApiConfig.getWebServices().getListPsg(orderId, authHeader)
        client.enqueue(object : Callback<List<PassengerListResponseItem>> {
            override fun onResponse(
                call: Call<List<PassengerListResponseItem>>,
                response: Response<List<PassengerListResponseItem>>
            ) {
                if (response.isSuccessful) {
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

    fun getAccPsgList(callback: PsgAccListCallback, orderId: Int, authHeader: String) {
        val client = ApiConfig.getWebServices().getAccPsgList(orderId, authHeader)
        client.enqueue(object : Callback<List<PassengerListResponseItem>> {
            override fun onResponse(
                call: Call<List<PassengerListResponseItem>>,
                response: Response<List<PassengerListResponseItem>>
            ) {
                if (response.isSuccessful) {
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

    //patch
    fun patchPsgAcc(id: Int, authHeader: String, callback: PsgAccPatchCallback) {
        val client = ApiConfig.getWebServices().patchPsgAcc(id, authHeader)
        client.enqueue(object : Callback<PatchResponse> {
            override fun onResponse(call: Call<PatchResponse>, response: Response<PatchResponse>) {
                if (response.isSuccessful) {
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

    fun patchPsgArrived(id: Int, authHeader: String, callback: PsgAccPatchCallback) {
        val client = ApiConfig.getWebServices().patchPsgArrived(id, authHeader)
        client.enqueue(object : Callback<PatchResponse> {
            override fun onResponse(call: Call<PatchResponse>, response: Response<PatchResponse>) {
                if (response.isSuccessful) {
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

    fun patchPsgStartRide(id: Int, authHeader: String, callback: PsgAccPatchCallback) {
        val client = ApiConfig.getWebServices().patchPsgStartRide(id, authHeader)
        client.enqueue(object : Callback<PatchResponse> {
            override fun onResponse(call: Call<PatchResponse>, response: Response<PatchResponse>) {
                if (response.isSuccessful) {
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

    fun patchPsgCompleteRide(id: Int, authHeader: String, callback: PsgAccPatchCallback) {
        val client = ApiConfig.getWebServices().patchPsgCompleteRide(id, authHeader)
        client.enqueue(object : Callback<PatchResponse> {
            override fun onResponse(call: Call<PatchResponse>, response: Response<PatchResponse>) {
                if (response.isSuccessful) {
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

    fun patchPsgDone(id: Int, authHeader: String, callback: PsgAccPatchCallback) {
        val client = ApiConfig.getWebServices().patchPsgDone(id, authHeader)
        client.enqueue(object : Callback<PatchResponse> {
            override fun onResponse(call: Call<PatchResponse>, response: Response<PatchResponse>) {
                if (response.isSuccessful) {
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

    //passenger
    fun postRecommendation(
        request: RecommendationRequest,
        authHeader: String,
        callback: PostRecommendationCallback
    ) {
        val client = ApiConfig.getWebServices().postRecommendation(request, authHeader)
        Log.d("REQUEST RECOMMENDATION", request.toString())
        Log.d("REQUEST RECOMMENDATION", authHeader)
        client.enqueue(object : Callback<RecommendationResponse> {
            override fun onResponse(
                call: Call<RecommendationResponse>,
                response: Response<RecommendationResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        callback.onRecommendationReceived(data.recommendations, data.psgData)
                    } else Log.d("Recommendation", "DATA NULL")
                } else Log.d("Recommendation", response.code().toString())
            }

            override fun onFailure(call: Call<RecommendationResponse>, t: Throwable) {
                Log.d("RecommendationFail", t.toString())
            }

        })
    }

    fun createPsg(
        request: CreatePsgRequest,
        authHeader: String,
        id: Int,
        callback: OnCreatePsgCallback
    ) {
        val client = ApiConfig.getWebServices().createPsg(request, authHeader, id)
        client.enqueue(object : Callback<CreatePsgResponse> {
            override fun onResponse(
                call: Call<CreatePsgResponse>,
                response: Response<CreatePsgResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        callback.onCreatePsgReceived(data)
                    } else Log.d("CREATE PSG", "DATA NULL")
                } else Log.d("CREATE PSG", response.code().toString())
            }

            override fun onFailure(call: Call<CreatePsgResponse>, t: Throwable) {
                Log.d("CREATE PSG", t.toString())
            }

        })
    }

    fun getOnGoingRoute(
        orderId: Int,
        authHeader: String,
        callback: GetOnGoingRouteCallback
    ) {
        val client = ApiConfig.getWebServices().getOnGoingRoute(orderId, authHeader)
        client.enqueue(object : Callback<List<Route>> {
            override fun onResponse(call: Call<List<Route>>, response: Response<List<Route>>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        callback.onOnGoingRouteReceived(data)
                    } else Log.d("ON GOING ROUTE", "DATA NULL")
                } else Log.d("ONGOING ROUTE", response.code().toString())
            }

            override fun onFailure(call: Call<List<Route>>, t: Throwable) {
                Log.d("OnGoingRoute", t.toString())
            }

        })

    }

    fun getDetailDriver(orderId: Int, authHeader: String, callback: GetDetailDriverCallback) {
        val client = ApiConfig.getWebServices().getDetailOrder(orderId, authHeader)
        client.enqueue(object : Callback<DetailOrderResponse> {
            override fun onResponse(
                call: Call<DetailOrderResponse>,
                response: Response<DetailOrderResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null){
                        callback.onDetailDriverReceived(data)
                    } else Log.d("DETAIL", "DATA NULL")
                } else Log.d("DETAIL", response.code().toString())
            }

            override fun onFailure(call: Call<DetailOrderResponse>, t: Throwable) {
                Log.d("DETAIL", t.toString())
            }

        })

    }

    fun getDetailPsg(psgId: Int, authHeader: String, callback: GetDetailPsgCallback) {
        val client = ApiConfig.getWebServices().getDetailPsg(psgId, authHeader)
        client.enqueue(object : Callback<DetailPsgResponse> {
            override fun onResponse(
                call: Call<DetailPsgResponse>,
                response: Response<DetailPsgResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null){
                        callback.onDetailPsgReceived(data)
                    } else Log.d("PSGDETAIL", "DATA NULL")
                }else Log.d("PSGDETAIL", response.code().toString())
            }

            override fun onFailure(call: Call<DetailPsgResponse>, t: Throwable) {
                Log.d("PSGDETAIL", t.toString())
            }

        })
    }

    fun patchArriving(orderId: Int, authHeader: String, callback: PsgAccPatchCallback) {
        val client = ApiConfig.getWebServices().patchArriving(orderId, "Bearer $authHeader")
        client.enqueue(object : Callback<PatchResponse> {
            override fun onResponse(call: Call<PatchResponse>, response: Response<PatchResponse>) {
                if (response.isSuccessful) {
                    Log.d("PATCH ARRIVING", "SUCCESS")
                    callback.onPsgAccReceived(true)
                } else {
                    callback.onPsgAccReceived(true)
                    Log.d("PATCH ARRIVING", response.code().toString())
                }
            }

            override fun onFailure(call: Call<PatchResponse>, t: Throwable) {
                Log.d("PATCH ARRIVING", t.toString())
            }

        })
    }


    interface GetDetailPsgCallback {
        fun onDetailPsgReceived(response: DetailPsgResponse)
    }

    interface GetDetailDriverCallback {
        fun onDetailDriverReceived(response: DetailOrderResponse)
    }

    interface GetOnGoingRouteCallback {
        fun onOnGoingRouteReceived(response: List<Route>)
    }

    interface OnCreatePsgCallback {
        fun onCreatePsgReceived(response: CreatePsgResponse)
    }

    interface PostRecommendationCallback {
        fun onRecommendationReceived(list: List<RecommendationsItem>, psgData: PsgData)
    }

    interface PsgAccPatchCallback {
        fun onPsgAccReceived(isSuccess: Boolean)
    }

    interface PsgAccListCallback {
        fun onPsgListReceived(response: List<PassengerListResponseItem>)
    }

    interface PassengerListCallback {
        fun onPsgListReceived(response: List<PassengerListResponseItem>)
    }

    interface DirectionCallback {
        fun onDirectionReceived(response: DirectionResponses?)
    }

    interface LoginCallback {
        fun onLoginReceived(isSucces: Boolean)
    }

    interface OrderCallback {
        fun onOrderReceived(response: OrderResponse)
    }
}