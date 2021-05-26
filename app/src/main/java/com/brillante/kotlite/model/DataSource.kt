package com.brillante.kotlite.model

import android.content.Context
import androidx.lifecycle.LiveData
import com.brillante.kotlite.data.local.entity.PassengerListEntity
import com.brillante.kotlite.model.direction.DirectionResponses
import com.brillante.kotlite.model.psgList.PassengerListResponseItem
import com.brillante.kotlite.preferences.SessionManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

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
}