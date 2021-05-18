package com.brillante.kotlite.util

import android.graphics.Color
import com.brillante.kotlite.model.direction.DirectionResponses
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil

object drawPolyLine {
    fun drawPolyLine(map: GoogleMap, response: DirectionResponses?){
        val shape = response?.routes?.get(0)?.overviewPolyline?.points
        val polyline = PolylineOptions()
            .addAll(PolyUtil.decode(shape))
            .width(8f)
            .color(Color.RED)
        map.addPolyline(polyline)
    }
}