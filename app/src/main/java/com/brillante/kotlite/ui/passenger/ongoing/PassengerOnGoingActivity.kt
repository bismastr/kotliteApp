package com.brillante.kotlite.ui.passenger.ongoing

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.brillante.kotlite.data.local.entity.CreatePsgEntity
import com.brillante.kotlite.data.local.entity.DetailDriverEntity
import com.brillante.kotlite.databinding.ActivityPassengerOnGoingBinding
import com.brillante.kotlite.preferences.SessionManager
import com.brillante.kotlite.ui.driver.invoce.DriverInvoiceViewModel
import com.brillante.kotlite.viewmodel.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.*
import java.util.*

class PassengerOnGoingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPassengerOnGoingBinding

    private lateinit var driverInvoiceViewModel: DriverInvoiceViewModel
    private lateinit var passengerOnGoingViewModel: PsgOnGoingViewModel

    private var psgId = 10

    private var isArrivingShow = false
    private var isArrivedShow = false
    private var isComplete = false
    private var isStart = false

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
        driverInvoiceViewModel =
            ViewModelProvider(this, factory)[DriverInvoiceViewModel::class.java]

        passengerOnGoingViewModel =
            ViewModelProvider(this, factory)[PsgOnGoingViewModel::class.java]

        val psgData = intent.getParcelableExtra<CreatePsgEntity>("PSG")
        if (psgData != null) {
            psgId = psgData.id
        }
        getData(psgData, authHeader)

        GlobalScope.launch {
            repeatDetail()
        }

    }

    private fun getData(psg: CreatePsgEntity?, authHeader: String) {
        if (psg != null) {
            driverInvoiceViewModel.getDetailDriver(psg.order, authHeader).observe(this, { Driver ->
                if (Driver != null) {
                    setData(Driver)
                }
            })
        }
    }

    private fun repeatDetail(): Job {
        return GlobalScope.launch(Dispatchers.IO) {
            while (isActive) {
                withContext(Dispatchers.Main) {
                    passengerOnGoingViewModel.getDetail(psgId, authHeader)
                        .observe(this@PassengerOnGoingActivity, { Psg ->
                            if (Psg != null) {
                                binding.tvStatus.text = Psg.status
                                var str = Psg.status
                                str = str.replace("\\s".toRegex(), "")
                                displayDialog(PsgStatus.valueOf(str.toUpperCase(Locale.ROOT)))
                            } else Log.d("PSG", "NULL")
                        })
                }
                delay(5000L)
            }
        }
    }

    private fun setData(driver: DetailDriverEntity) {
        passengerOnGoingViewModel.getDetail(psgId, authHeader).observe(this, { Psg ->
            if (Psg != null) {
                binding.tvStatus.text = Psg.status

            } else Log.d("PSG", "NULL")
        })

        passengerOnGoingViewModel.getDetail(psgId, authHeader)
        binding.tvDriverName.text = driver.firstName

        binding.tvTime.text = driver.time
    }

    enum class PsgStatus(val type: String) {
        PENDING("Pending"),
        ARRIVING("Arriving"),
        ARRIVED("Arrived"),
        COMPLETERIDE("Complete Ride"),
        STARTRIDE("Start Ride"),
    }

    private fun displayDialog(status: PsgStatus) {
        when (status) {
            PsgStatus.PENDING -> Log.d("PENDING", "PENDING")
            PsgStatus.ARRIVING -> dialogArriving()
            PsgStatus.ARRIVED -> dialogArrived(isArrivedShow)
            PsgStatus.COMPLETERIDE -> dialogCompleteRide()
            PsgStatus.STARTRIDE -> dialogStartRide()
        }
    }

    private fun dialogArriving() {
        if (!isArrivingShow) {
            isArrivingShow = true
            MaterialAlertDialogBuilder(this)
                .setTitle("Driver Arriving")
                .setMessage("Your Driver Already Arrived in your Location")
                .setPositiveButton("OK") { dialog, which ->

                }
                .show()
        }
    }

    private fun dialogArrived(shown: Boolean) {
        if (!shown) {
            isArrivedShow = true
            MaterialAlertDialogBuilder(this)
                .setTitle("Driver Arrived")
                .setMessage("Your Driver Already Arrived in your Location")
                .setPositiveButton("OK") { dialog, which ->

                }
                .show()
        }
    }

    private fun dialogStartRide() {
        if (!isStart) {
            isComplete = true
            MaterialAlertDialogBuilder(this)
                .setTitle("Start Ride")
                .setMessage("Your Ride is Started")
                .setPositiveButton("OK") { dialog, which ->

                }
                .show()
        }
    }

    private fun dialogCompleteRide() {
        if (!isComplete) {
            isComplete = true
            MaterialAlertDialogBuilder(this)
                .setTitle("Complete Ride")
                .setMessage("Your Ride is Completed")
                .setPositiveButton("OK") { dialog, which ->

                }
                .show()
        }
    }

    private fun dialogDone() {

    }


}