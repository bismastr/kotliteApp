package com.brillante.kotlite.model

import android.content.Context
import androidx.lifecycle.LiveData
import com.brillante.kotlite.model.direction.DirectionResponses
import com.brillante.kotlite.preferences.SessionManager
import com.google.android.gms.maps.GoogleMap

interface DataSource {

    fun getDirection(from: String, to: String, map: GoogleMap): Unit

    fun login(username: String, password: String, sessionManager: SessionManager, context: Context): LiveData<Boolean>

}