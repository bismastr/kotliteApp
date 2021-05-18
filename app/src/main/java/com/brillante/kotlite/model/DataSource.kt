package com.brillante.kotlite.model

import androidx.lifecycle.LiveData
import com.brillante.kotlite.model.direction.DirectionResponses
import com.google.android.gms.maps.GoogleMap

interface DataSource {

    fun getDirection(from: String, to: String, map: GoogleMap): Unit
}