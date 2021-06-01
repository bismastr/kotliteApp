package com.brillante.kotlite.ui.passenger.driverList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brillante.kotlite.data.Repository
import com.brillante.kotlite.data.local.entity.CreatePsgEntity
import com.brillante.kotlite.data.local.entity.PsgDataEntity
import com.brillante.kotlite.data.local.entity.RecommendationEntity
import com.brillante.kotlite.data.remote.model.recomendation.RecommendationRequest

class DriverListViewModel(private val repository: Repository) : ViewModel() {

    fun getDriverList(
        request: RecommendationRequest,
        authHeader: String
    ): LiveData<RecommendationEntity> {
        return repository.recommendation(request, authHeader)
    }

    fun createPsg(
        request: PsgDataEntity,
        authHeader: String,
        orderId: Int
    ): LiveData<CreatePsgEntity> {
        return repository.createPsg(request, authHeader, orderId)
    }


}