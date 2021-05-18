package com.brillante.kotlite.model

import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brillante.kotlite.BuildConfig
import com.brillante.kotlite.model.direction.DirectionResponses
import com.brillante.kotlite.preferences.SessionManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil

class Repository private constructor(private val remoteDataSource: RemoteDataSource) : DataSource {
    companion object {
        const val KEY = BuildConfig.MAPS_API_KEY

        @Volatile
        private var instance: Repository? = null

        fun getInstance(remoteDataSource: RemoteDataSource): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(remoteDataSource).apply { instance = this }
            }


    }

    override fun getDirection(from: String, to: String, map: GoogleMap) {
        remoteDataSource.getDirection(from, to, KEY, object : RemoteDataSource.DirectionCallback {
            override fun onDirectionReceived(response: DirectionResponses?) {
                if (response?.status != "REQUEST_DENIED") {

                    Log.d("SHAPE", response.toString())

                    val shape = response?.routes?.get(0)?.overviewPolyline?.points
                    map.clear()
                    val polyline = map.addPolyline(PolylineOptions()
                        .addAll(PolyUtil.decode(shape))
                        .width(8f)
                        .color(Color.BLUE))

                    val boundsBuilder = LatLngBounds.builder()
                    for (latLngPoint: LatLng in polyline.points) {
                        boundsBuilder.include(latLngPoint)
                    }

                    val routePadding = 100
                    val latLngBound = boundsBuilder.build()

                    map.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(latLngBound, routePadding), 600, null
                    )

                } else {
                    Log.d("ERROR", "ERROR REPOSITORY DIRECTION")
                }
            }

        })
    }

    override fun login(
        username: String,
        password: String,
        sessionManager: SessionManager,
        context: Context
    ): LiveData<Boolean> {
        val isLogin = MutableLiveData<Boolean>()
        remoteDataSource.loginUser(username, password, sessionManager, context, object : RemoteDataSource.LoginCallback{
            override fun onLoginReceived(isSucces: Boolean) {
                isLogin.postValue(isSucces)
            }

        })
        return isLogin
    }


}