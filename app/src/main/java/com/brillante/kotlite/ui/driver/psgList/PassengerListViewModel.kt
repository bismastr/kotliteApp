package com.brillante.kotlite.ui.driver.psgList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.brillante.kotlite.data.local.entity.PassengerListEntity
import com.brillante.kotlite.model.Repository

class PassengerListViewModel(private val repository: Repository): ViewModel() {

    fun getPsgList(): LiveData<List<PassengerListEntity>>{
        return repository.getPsgList()
    }

    fun getAccListPsg(): LiveData<List<PassengerListEntity>> {
        return repository.getAccPsgList()
    }

    fun patchAccPsg(id: Int): LiveData<Boolean>{
        return repository.patchAccPsg(id)
    }
}