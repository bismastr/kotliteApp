package com.brillante.kotlite.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.brillante.kotlite.model.Repository
import com.google.android.gms.maps.GoogleMap

class MapViewModel(private val repository: Repository): ViewModel() {

    fun getDirection(from: String, to: String, map: GoogleMap){
        return repository.getDirection(from, to, map)
    }
}