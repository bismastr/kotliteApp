package com.brillante.kotlite.ui.driver.ongoing

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.brillante.kotlite.data.Repository

class PassengerOnGoingViewModel(private val repository: Repository) : ViewModel() {

    fun patchArrived(id: Int): LiveData<Boolean> {
        return repository.patchPsgArrived(id)
    }

    fun patchStart(id: Int): LiveData<Boolean> {
        return repository.patchPsgStartRide(id)
    }

    fun patchComplete(id: Int): LiveData<Boolean> {
        return repository.patchPsgCompleteRide(id)
    }

    fun patchDone(id: Int): LiveData<Boolean> {
        return repository.patchPsgDone(id)
    }
}