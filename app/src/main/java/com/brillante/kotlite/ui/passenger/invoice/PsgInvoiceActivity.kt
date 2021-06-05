package com.brillante.kotlite.ui.passenger.invoice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.brillante.kotlite.R
import com.brillante.kotlite.data.remote.model.detailpsg.DetailPsgResponse
import com.brillante.kotlite.databinding.ActivityDriverInvoiceBinding
import com.brillante.kotlite.databinding.ActivityPsgInvoiceBinding
import com.brillante.kotlite.preferences.SessionManager
import com.brillante.kotlite.ui.passenger.ongoing.PsgOnGoingViewModel
import com.brillante.kotlite.viewmodel.ViewModelFactory

class PsgInvoiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPsgInvoiceBinding

    private lateinit var viewModelOnGoingViewModel: PsgOnGoingViewModel

    private var psgId: Int = 0
    private lateinit var sessionManager: SessionManager
    private lateinit var authHeader: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPsgInvoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance()
        viewModelOnGoingViewModel = ViewModelProvider(this, factory)[PsgOnGoingViewModel::class.java]
        //sesionmanager
        sessionManager = SessionManager(this)
        authHeader = sessionManager.fetchAuthToken().toString()
        //get intent
        psgId = intent.getIntExtra("psgId", 0)

        getData()

    }

    private fun getData() {

        viewModelOnGoingViewModel.getDetail(psgId, authHeader).observe(this, { Psg ->
            if (Psg != null) {
                setData(Psg)
            } else Log.d("PSG DETAIL", "PSG NULL")
        })
    }

    private fun setData(psg: DetailPsgResponse){
        val fistName = "Good job, ${psg.firstName}"
        val income = "Rp. ${psg.fee}"
        binding.tvNameDriver.text = fistName
        binding.tvIncome.text = income
        binding.tvCartype.text = psg.distance.toString()
        binding.tvPassenger.text = psg.timeTaken.toString()
        binding.tvTime.text = psg.time
    }
}