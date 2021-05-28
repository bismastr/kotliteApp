package com.brillante.kotlite.model

import android.content.Context
import androidx.lifecycle.LiveData
import com.brillante.kotlite.data.local.entity.PassengerListEntity
import com.brillante.kotlite.model.direction.DirectionResponses
import com.brillante.kotlite.model.psgList.PassengerListResponseItem
import com.brillante.kotlite.model.psgList.patch.PatchResponse
import com.brillante.kotlite.preferences.SessionManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.http.PATCH
import retrofit2.http.Path

interface DataSource {

    fun getDirection(from: String, to: String, map: GoogleMap): Unit

    fun login(
        username: String,
        password: String,
        sessionManager: SessionManager,
        context: Context
    ): LiveData<Boolean>

    fun createOrder(
        latLngStart: LatLng?,
        latLngEnd: LatLng?,
        time: String,
        capacity: Int,
        carType: String,
        context: Context,
        authHeader: String,
    ): LiveData<Boolean>

    fun getPsgList(): LiveData<List<PassengerListEntity>>

    fun getAccPsgList(): LiveData<List<PassengerListEntity>>

    fun patchAccPsg(id: Int): LiveData<Boolean>

    //status
    fun patchPsgArrived(id: Int): LiveData<Boolean>
    fun patchPsgStartRide(id: Int): LiveData<Boolean>
    fun patchPsgCompleteRide(id: Int): LiveData<Boolean>
    fun patchPsgDone(id: Int): LiveData<Boolean>
}