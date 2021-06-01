package com.brillante.kotlite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brillante.kotlite.data.Repository
import com.brillante.kotlite.ui.login.LoginViewModel
import com.brillante.kotlite.ui.MapViewModel
import com.brillante.kotlite.ui.driver.invoce.DriverInvoiceViewModel
import com.brillante.kotlite.ui.driver.ongoing.PassengerOnGoingViewModel
import com.brillante.kotlite.ui.driver.psgList.PassengerListViewModel
import com.brillante.kotlite.ui.passenger.driverList.DriverListViewModel
import com.brillante.kotlite.util.Injection

class ViewModelFactory private constructor(private val mRepository: Repository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository()).apply {
                    instance = this
                }
            }


    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MapViewModel::class.java) -> {
                MapViewModel(mRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(mRepository) as T
            }
            modelClass.isAssignableFrom(PassengerListViewModel::class.java) -> {
                PassengerListViewModel(mRepository) as T
            }
            modelClass.isAssignableFrom(PassengerOnGoingViewModel::class.java) -> {
                PassengerOnGoingViewModel(mRepository) as T
            }
            modelClass.isAssignableFrom(DriverListViewModel::class.java) -> {
                DriverListViewModel(mRepository) as T
            }
            modelClass.isAssignableFrom(DriverInvoiceViewModel::class.java)-> {
                DriverInvoiceViewModel(mRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}