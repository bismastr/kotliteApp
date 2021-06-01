package com.brillante.kotlite.ui.passenger.ongoing

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.brillante.kotlite.data.Repository
import com.brillante.kotlite.data.remote.model.detailpsg.DetailPsgResponse

class PsgOnGoingViewModel(private val repository: Repository): ViewModel() {

    fun getDetail(psgId: Int, authHeader: String): LiveData<DetailPsgResponse> {
        return repository.getDetailPsg(psgId, authHeader)
    }
}