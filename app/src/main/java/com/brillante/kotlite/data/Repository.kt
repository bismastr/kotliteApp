package com.brillante.kotlite.data

import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brillante.kotlite.BuildConfig
import com.brillante.kotlite.data.local.entity.*
import com.brillante.kotlite.data.remote.model.createpsg.CreatePsgRequest
import com.brillante.kotlite.data.remote.model.createpsg.CreatePsgResponse
import com.brillante.kotlite.data.remote.model.direction.DirectionResponses
import com.brillante.kotlite.data.remote.model.order.OrderResponse
import com.brillante.kotlite.data.remote.model.psgList.PassengerListResponseItem
import com.brillante.kotlite.data.remote.model.recomendation.PsgData
import com.brillante.kotlite.data.remote.model.recomendation.RecommendationRequest
import com.brillante.kotlite.data.remote.model.recomendation.RecommendationsItem
import com.brillante.kotlite.preferences.SessionManager
import com.brillante.kotlite.util.DataMapper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil

class Repository private constructor(private val remoteDataSource: RemoteDataSource) : DataSource {
    companion object {
        const val KEY = BuildConfig.API_KEY

        @Volatile
        private var instance: Repository? = null

        fun getInstance(remoteDataSource: RemoteDataSource): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(remoteDataSource).apply { instance = this }
            }


    }

    override fun getDirection(from: String, to: String, map: GoogleMap) {
        remoteDataSource.getDirection(from, to, KEY, object : RemoteDataSource.DirectionCallback {
            override fun onDirectionReceived(response: DirectionResponses?) {
                if (response?.status != "REQUEST_DENIED") {

                    Log.d("SHAPE", response.toString())
                    val distance = response?.routes?.get(0)?.legs?.get(0)?.distance
                    Log.d("SHAPE", distance.toString())
                    val duration = response?.routes?.get(0)?.legs?.get(0)?.duration
                    Log.d("DURATION", duration.toString())
                    val shape = response?.routes?.get(0)?.overviewPolyline?.points
                    map.clear()
                    val polyline = map.addPolyline(
                        PolylineOptions()
                            .addAll(PolyUtil.decode(shape))
                            .width(8f)
                            .color(Color.BLUE)
                    )


                    val boundsBuilder = LatLngBounds.builder()
                    for (latLngPoint: LatLng in polyline.points) {
                        boundsBuilder.include(latLngPoint)
                    }

                    val routePadding = 100
                    val latLngBound = boundsBuilder.build()

                    map.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(latLngBound, routePadding), 600, null
                    )

                } else {
                    Log.d("ERROR", "ERROR REPOSITORY DIRECTION")
                }
            }

        })
    }

    override fun login(
        username: String,
        password: String,
        sessionManager: SessionManager,
        context: Context
    ): LiveData<Boolean> {
        val isLogin = MutableLiveData<Boolean>()
        remoteDataSource.loginUser(
            username,
            password,
            sessionManager,
            context,
            object : RemoteDataSource.LoginCallback {
                override fun onLoginReceived(isSucces: Boolean) {
                    isLogin.postValue(isSucces)
                }

            })
        return isLogin
    }

    override fun createOrder(
        latLngStart: LatLng?,
        latLngEnd: LatLng?,
        time: String,
        capacity: Int,
        carType: String,
        context: Context,
        authHeader: String,
    ): LiveData<OrderEntity> {
        val orderResult = MutableLiveData<OrderEntity>()
        if (latLngEnd !== null && latLngStart !== null) {
            val longStart: String = latLngStart.longitude.toString()
            val latStart: String = latLngStart.latitude.toString()
            val longEnd: String = latLngEnd.longitude.toString()
            val latEnd: String = latLngEnd.latitude.toString()
            remoteDataSource.createOrder(
                latStart,
                longStart,
                latEnd,
                longEnd,
                time,
                capacity,
                carType,
                context,
                object : RemoteDataSource.OrderCallback {
                    override fun onOrderReceived(response: OrderResponse) {
                        val order = OrderEntity(
                            id = response.id
                        )
                        orderResult.postValue(order)
                    }

                }, "Bearer $authHeader"
            )
            return orderResult
        } else
            return orderResult
    }

    override fun getPsgList(orderId: Int, authHeader: String): LiveData<List<PassengerListEntity>> {
        val psgListResult = MutableLiveData<List<PassengerListEntity>>()
        remoteDataSource.getPassengerList(object : RemoteDataSource.PassengerListCallback {
            override fun onPsgListReceived(response: List<PassengerListResponseItem>) {
                val psgList = ArrayList<PassengerListEntity>()
                for (i in response) {
                    val psg = PassengerListEntity(
                        i.distance,
                        i.longPick,
                        i.fee,
                        i.lastName,
                        i.longDrop,
                        i.timeTaken,
                        i.phone,
                        i.latDrop,
                        i.id,
                        i.time,
                        i.firstName,
                        i.latPick,
                        i.status,
                        i.order,
                        i.placePick,
                        i.placeDrop
                    )
                    psgList.add(psg)
                }
                psgListResult.postValue(psgList)
            }
        }, orderId, "Bearer $authHeader")
        return psgListResult
    }

    override fun getAccPsgList(
        orderId: Int,
        authHeader: String
    ): LiveData<List<PassengerListEntity>> {
        val psgListResult = MutableLiveData<List<PassengerListEntity>>()
        remoteDataSource.getAccPsgList(object : RemoteDataSource.PsgAccListCallback {
            override fun onPsgListReceived(response: List<PassengerListResponseItem>) {
                val psgList = ArrayList<PassengerListEntity>()
                for (i in response) {
                    val psg = PassengerListEntity(
                        i.distance,
                        i.longPick,
                        i.fee,
                        i.lastName,
                        i.longDrop,
                        i.timeTaken,
                        i.phone,
                        i.latDrop,
                        i.id,
                        i.time,
                        i.firstName,
                        i.latPick,
                        i.status,
                        i.order,
                        i.placePick,
                        i.placeDrop
                    )
                    psgList.add(psg)
                }
                psgListResult.postValue(psgList)
            }

        }, orderId, "Bearer $authHeader")
        return psgListResult
    }

    //patch
    override fun patchAccPsg(id: Int): LiveData<Boolean> {
        val patchAcc = MutableLiveData<Boolean>()
        remoteDataSource.patchPsgAcc(id, object : RemoteDataSource.PsgAccPatchCallback {
            override fun onPsgAccReceived(isSuccess: Boolean) {
                patchAcc.postValue(isSuccess)
            }

        })
        return patchAcc
    }

    override fun patchPsgArrived(id: Int): LiveData<Boolean> {
        val patchAcc = MutableLiveData<Boolean>()
        remoteDataSource.patchPsgArrived(id, object : RemoteDataSource.PsgAccPatchCallback {
            override fun onPsgAccReceived(isSuccess: Boolean) {
                patchAcc.postValue(isSuccess)
            }

        })
        return patchAcc
    }

    override fun patchPsgStartRide(id: Int): LiveData<Boolean> {
        val patchAcc = MutableLiveData<Boolean>()
        remoteDataSource.patchPsgStartRide(id, object : RemoteDataSource.PsgAccPatchCallback {
            override fun onPsgAccReceived(isSuccess: Boolean) {
                patchAcc.postValue(isSuccess)
            }

        })
        return patchAcc
    }

    override fun patchPsgCompleteRide(id: Int): LiveData<Boolean> {
        val patchAcc = MutableLiveData<Boolean>()
        remoteDataSource.patchPsgCompleteRide(id, object : RemoteDataSource.PsgAccPatchCallback {
            override fun onPsgAccReceived(isSuccess: Boolean) {
                patchAcc.postValue(isSuccess)
            }

        })
        return patchAcc
    }

    override fun patchPsgDone(id: Int): LiveData<Boolean> {
        val patchAcc = MutableLiveData<Boolean>()
        remoteDataSource.patchPsgDone(id, object : RemoteDataSource.PsgAccPatchCallback {
            override fun onPsgAccReceived(isSuccess: Boolean) {
                patchAcc.postValue(isSuccess)
            }

        })
        return patchAcc
    }

    override fun recommendation(
        request: RecommendationRequest,
        authHeader: String
    ): LiveData<RecommendationEntity> {
        val recommendationResult = MutableLiveData<RecommendationEntity>()

        remoteDataSource.postRecommendation(
            request, "Bearer $authHeader",
            object : RemoteDataSource.PostRecommendationCallback {
                override fun onRecommendationReceived(
                    list: List<RecommendationsItem>,
                    psgData: PsgData
                ) {

                    val data = RecommendationEntity(
                        psgData = DataMapper.psgDataToEntity(psgData),
                        recommendations = DataMapper.driverListToEntity(list)
                    )
                    recommendationResult.postValue(data)
                }

            })
        return recommendationResult
    }

    override fun createPsg(
        request: PsgDataEntity,
        authHeader: String,
        orderId: Int
    ): LiveData<CreatePsgEntity> {
        val createPsgResult = MutableLiveData<CreatePsgEntity>()
        val requestBody = CreatePsgRequest(
            request.distance,
            request.longPick,
            request.minimumFee,
            request.longDrop,
            request.timeTaken,
            request.latDrop,
            request.placeDrop,
            request.maximumFee,
            request.placePick,
            request.time,
            request.user,
            request.latPick,
            request.status
        )
        remoteDataSource.createPsg(
            requestBody,
            "Bearer $authHeader",
            orderId,
            object : RemoteDataSource.OnCreatePsgCallback {
                override fun onCreatePsgReceived(response: CreatePsgResponse) {
                    val data = DataMapper.createPsgToEntity(response)
                    createPsgResult.postValue(data)
                }
            })
        return createPsgResult
    }


}