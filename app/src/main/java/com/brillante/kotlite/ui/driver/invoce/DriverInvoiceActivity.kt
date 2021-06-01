package com.brillante.kotlite.ui.driver.invoce

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.brillante.kotlite.data.local.entity.DetailDriverEntity
import com.brillante.kotlite.data.local.entity.OrderEntity
import com.brillante.kotlite.databinding.ActivityDriverInvoiceBinding
import com.brillante.kotlite.preferences.SessionManager
import com.brillante.kotlite.ui.role.RoleActivity
import com.brillante.kotlite.viewmodel.ViewModelFactory

class DriverInvoiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDriverInvoiceBinding

    private var orderId: Int = 0
    private lateinit var sessionManager: SessionManager
    private lateinit var authHeader: String

    private lateinit var driverInvoiceViewModel: DriverInvoiceViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverInvoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //sesionmanager
        sessionManager = SessionManager(this)
        authHeader = sessionManager.fetchAuthToken().toString()
        //viewmodel
        val factory = ViewModelFactory.getInstance()
        driverInvoiceViewModel =
            ViewModelProvider(this, factory)[DriverInvoiceViewModel::class.java]
        //getIntent
        val order = intent.getParcelableExtra<OrderEntity>("ORDER")
        if (order != null) {
            orderId = order.id
        }

        getData()

        binding.btnFinishRide.setOnClickListener {
            val intent = Intent(this, RoleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getData() {
        Log.d("GETDATA", "INIT")

        driverInvoiceViewModel.getDetailDriver(orderId, authHeader).observe(this, { Driver ->
            if (Driver != null) {
                setData(Driver)
            } else Log.d("DRIVER DETAIL", "DRIVER NULL")
        })
    }

    private fun setData(driver: DetailDriverEntity) {
        val fistName = "Good job, ${driver.firstName}"
        val income = "Rp. ${driver.income}"
        binding.tvNameDriver.text = fistName
        binding.tvIncome.text = income
        binding.tvCartype.text = driver.carType
        binding.tvPassenger.text = driver.totalPsg.toString()
        binding.tvTime.text = driver.time
    }
}