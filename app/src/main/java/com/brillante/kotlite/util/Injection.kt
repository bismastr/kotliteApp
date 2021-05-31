package com.brillante.kotlite.util

import com.brillante.kotlite.data.RemoteDataSource
import com.brillante.kotlite.data.Repository

object Injection {
    fun provideRepository(): Repository {

        val remoteDataSource = RemoteDataSource.getInstance()
        return Repository.getInstance(remoteDataSource)
    }
}