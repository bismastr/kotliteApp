package com.brillante.kotlite.ui.driver.ongoing

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.brillante.kotlite.data.Repository
import com.google.android.gms.maps.GoogleMap

class PassengerOnGoingViewModel(private val repository: Repository) : ViewModel() {

    fun patchArrived(id: Int, authHeader: String): LiveData<Boolean> {
        return repository.patchPsgArrived(id, authHeader)
    }

    fun patchStart(id: Int, authHeader: String): LiveData<Boolean> {
        return repository.patchPsgStartRide(id, authHeader)
    }

    fun patchComplete(id: Int, authHeader: String): LiveData<Boolean> {
        return repository.patchPsgCompleteRide(id, authHeader)
    }

    fun patchDone(id: Int, authHeader: String): LiveData<Boolean> {
        return repository.patchPsgDone(id, authHeader)
    }

    fun getRoute(orderId: Int, authHeader: String, map: GoogleMap){
        return repository.getOnGoingRoute(orderId, authHeader, map)
    }
}