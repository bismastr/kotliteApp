package com.brillante.kotlite.util

import com.brillante.kotlite.model.RemoteDataSource
import com.brillante.kotlite.model.Repository

object Injection {
    fun provideRepository(): Repository {

        val remoteDataSource = RemoteDataSource.getInstance()
        return Repository.getInstance(remoteDataSource)
    }
}