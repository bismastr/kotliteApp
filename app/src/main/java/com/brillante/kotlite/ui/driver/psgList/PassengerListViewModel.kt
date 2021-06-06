package com.brillante.kotlite.ui.driver.psgList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.brillante.kotlite.data.Repository
import com.brillante.kotlite.data.local.entity.PassengerListEntity

class PassengerListViewModel(private val repository: Repository) : ViewModel() {

    fun getPsgList(orderId: Int, authHeader: String): LiveData<List<PassengerListEntity>> {
        return repository.getPsgList(orderId, authHeader)
    }

    fun getAccListPsg(orderId: Int, authHeader: String): LiveData<List<PassengerListEntity>> {
        return repository.getAccPsgList(orderId, authHeader)
    }

    fun patchAccPsg(id: Int, authHeader: String): LiveData<Boolean> {
        return repository.patchAccPsg(id, authHeader)
    }

    fun patchArriving(orderId: Int, authHeader: String): LiveData<Boolean> {
        return repository.patchArriving(orderId, authHeader)
    }
}