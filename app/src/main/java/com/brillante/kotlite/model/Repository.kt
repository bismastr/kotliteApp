package com.brillante.kotlite.model

import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brillante.kotlite.BuildConfig
import com.brillante.kotlite.data.local.entity.PassengerListEntity
import com.brillante.kotlite.model.direction.DirectionResponses
import com.brillante.kotlite.model.psgList.PassengerListResponseItem
import com.brillante.kotlite.preferences.SessionManager
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
    ): LiveData<Boolean> {
        val isCreated = MutableLiveData<Boolean>()
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
                    override fun onOrderReceived(isSucces: Boolean) {
                        isCreated.postValue(isSucces)
                    }

                }, "Bearer " + authHeader
            )
            return isCreated
        } else isCreated.postValue(false)

        return isCreated
    }

    override fun getPsgList(): LiveData<List<PassengerListEntity>> {
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
                        i.order
                    )
                    psgList.add(psg)
                }
                psgListResult.postValue(psgList)
            }
        })
        return psgListResult
    }

    override fun getAccPsgList(): LiveData<List<PassengerListEntity>> {
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
                        i.order
                    )
                    psgList.add(psg)
                }
                psgListResult.postValue(psgList)
            }

        })
        return psgListResult
    }

    override fun patchAccPsg(id: Int): LiveData<Boolean> {
        val patchAcc = MutableLiveData<Boolean>()
        remoteDataSource.patchPsgAcc(id, object : RemoteDataSource.PsgAccPatchCallback {
            override fun onPsgAccReceived(isSuccess: Boolean) {
                patchAcc.postValue(isSuccess)
            }

        })
        return patchAcc
    }


}