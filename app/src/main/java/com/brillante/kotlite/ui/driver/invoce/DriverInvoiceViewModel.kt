package com.brillante.kotlite.ui.driver.invoce

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.brillante.kotlite.data.Repository
import com.brillante.kotlite.data.local.entity.DetailDriverEntity

class DriverInvoiceViewModel(private val repository: Repository) : ViewModel() {

    fun getDetailDriver(orderId: Int, authHeader: String): LiveData<DetailDriverEntity> {
        return repository.getDetailDriver(orderId , authHeader)
    }
}