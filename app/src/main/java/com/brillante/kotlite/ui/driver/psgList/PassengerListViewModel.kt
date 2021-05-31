package com.brillante.kotlite.ui.driver.psgList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.brillante.kotlite.data.Repository
import com.brillante.kotlite.data.local.entity.PassengerListEntity

class PassengerListViewModel(private val repository: Repository) : ViewModel() {

    fun getPsgList(orderId: Int, authHeader: String): LiveData<List<PassengerListEntity>> {
        return repository.getPsgList(7, authHeader)
    }

    fun getAccListPsg(orderId: Int, authHeader: String): LiveData<List<PassengerListEntity>> {
        return repository.getAccPsgList(7, authHeader)
    }

    fun patchAccPsg(id: Int): LiveData<Boolean> {
        return repository.patchAccPsg(7)
    }

}