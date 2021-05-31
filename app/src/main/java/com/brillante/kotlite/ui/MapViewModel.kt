package com.brillante.kotlite.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.brillante.kotlite.data.Repository
import com.brillante.kotlite.data.local.entity.OrderEntity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

class MapViewModel(private val repository: Repository) : ViewModel() {

    fun getDirection(from: String, to: String, map: GoogleMap) {
        return repository.getDirection(from, to, map)
    }

    fun createOrder(
        latLngStart: LatLng?,
        latLngEnd: LatLng?,
        time: String,
        capacity: Int,
        carType: String,
        context: Context,
        authHeader: String
    ): LiveData<OrderEntity> {
        return repository.createOrder(latLngStart, latLngEnd, time, capacity, carType, context, authHeader)
    }
}