package com.brillante.kotlite.util

import com.brillante.kotlite.data.local.entity.CreatePsgEntity
import com.brillante.kotlite.data.local.entity.PsgDataEntity
import com.brillante.kotlite.data.local.entity.RecommendationsItemEntity
import com.brillante.kotlite.data.remote.model.createpsg.CreatePsgResponse
import com.brillante.kotlite.data.remote.model.recomendation.PsgData
import com.brillante.kotlite.data.remote.model.recomendation.RecommendationsItem

object DataMapper {
    fun psgDataToEntity(data: PsgData): PsgDataEntity {
        return PsgDataEntity(
            data.distance,
            data.longPick,
            data.minimumFee,
            data.longDrop,
            data.timeTaken,
            data.latDrop,
            data.placeDrop,
            data.maximumFee,
            data.placePick,
            data.time,
            data.user,
            data.latPick,
            data.status
        )
    }

    fun driverListToEntity(data: List<RecommendationsItem>): List<RecommendationsItemEntity> {
        val driverList = ArrayList<RecommendationsItemEntity>()
        for (i in data) {
            val driver = RecommendationsItemEntity(
                i.income,
                i.placeStart,
                i.longEnd,
                i.latStart,
                i.lastName,
                i.longStart,
                i.latEnd,
                i.capacity,
                i.carType,
                i.placeEnd,
                i.totalPsg,
                "08",
                i.id,
                i.time,
                i.firstName,
                i.user,
                i.status

            )
            driverList.add(driver)
        }
        return driverList
    }

    fun createPsgToEntity(data: CreatePsgResponse): CreatePsgEntity {
        return CreatePsgEntity(
            data.id,
            data.time,
            data.status,
            data.carType,
            data.totalPsg,
            data.placeStart,
            data.placeEnd,
            data.capacity,
        )
    }
}