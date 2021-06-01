package com.brillante.kotlite.ui.passenger.ongoing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.brillante.kotlite.data.local.entity.CreatePsgEntity
import com.brillante.kotlite.data.local.entity.DetailDriverEntity
import com.brillante.kotlite.databinding.ActivityPassengerOnGoingBinding
import com.brillante.kotlite.preferences.SessionManager
import com.brillante.kotlite.ui.driver.invoce.DriverInvoiceViewModel
import com.brillante.kotlite.ui.driver.ongoing.PassengerOnGoingViewModel
import com.brillante.kotlite.viewmodel.ViewModelFactory

class PassengerOnGoingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPassengerOnGoingBinding

    private lateinit var driverInvoiceViewModel: DriverInvoiceViewModel

    private lateinit var sessionManager: SessionManager
    private lateinit var authHeader: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPassengerOnGoingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //sessionmanager
        sessionManager = SessionManager(this)
        authHeader = sessionManager.fetchAuthToken().toString()
        //factory
        val factory = ViewModelFactory.getInstance()
        driverInvoiceViewModel = ViewModelProvider(this, factory)[DriverInvoiceViewModel::class.java]
        val psgData = intent.getParcelableExtra<CreatePsgEntity>("PSG")
        getData(psgData, authHeader)
    }

    private fun getData(psg: CreatePsgEntity?, authHeader: String){
        if (psg != null) {
            driverInvoiceViewModel.getDetailDriver(psg.order, authHeader).observe(this, {Driver ->
                if (Driver != null){
                    setData(Driver, psg)
                }
            })
        }
    }

    private fun setData(driver: DetailDriverEntity, psg: CreatePsgEntity) {
        binding.tvDriverName.text = driver.firstName
        binding.tvStatus.text = psg.status
        binding.tvTime.text = driver.time
    }
}