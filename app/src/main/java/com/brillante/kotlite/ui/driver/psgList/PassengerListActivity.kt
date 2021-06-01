package com.brillante.kotlite.ui.driver.psgList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brillante.kotlite.data.local.entity.OrderEntity
import com.brillante.kotlite.data.local.entity.PassengerListEntity
import com.brillante.kotlite.databinding.ActivityPassengerListBinding
import com.brillante.kotlite.preferences.SessionManager
import com.brillante.kotlite.ui.driver.ongoing.DriverOnGoingActivity
import com.brillante.kotlite.ui.driver.psgList.adapter.PsgListAccAdapter
import com.brillante.kotlite.viewmodel.ViewModelFactory

class PassengerListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPassengerListBinding
    private lateinit var passengerListViewModel: PassengerListViewModel
    private lateinit var adapterPsgList: PsgListAccAdapter

    private var orderId: Int = 0

    private lateinit var sessionManager: SessionManager
    private lateinit var authHeader: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPassengerListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //sessionmanager
        sessionManager = SessionManager(this)
        authHeader = sessionManager.fetchAuthToken().toString()
        //intent
        val order = intent.getParcelableExtra<OrderEntity>("ORDER")
        if (order != null) {
            orderId = order.id
        }
        //fragment pending list
        val pendingListFragment = PendingListFragment().apply { inject(orderId) {
            if (it == PendingListFragment.Status.ACCEPTED){
                getData()
            }
        } }
        binding.btnPendingList.setOnClickListener {
            pendingListFragment.show(supportFragmentManager, "PendingList")
        }
        //clickBtnRide
        binding.btnRide.setOnClickListener {
            passengerListViewModel.patchArriving(orderId, authHeader).observe(this, {Arriving ->
                if (Arriving){
                    val intent = Intent(this, DriverOnGoingActivity::class.java)
                    intent.putExtra("ORDER", order)
                    startActivity(intent)
                }
            })

        }
        //viewmodel
        val factory = ViewModelFactory.getInstance()
        passengerListViewModel =
            ViewModelProvider(this, factory)[PassengerListViewModel::class.java]

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapterPsgList = PsgListAccAdapter()
        binding.rvAcceptedList.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvAcceptedList.adapter = adapterPsgList
        getData()
    }

    private fun getData() {
        passengerListViewModel.getAccListPsg(orderId, authHeader).observe(this, { Psg ->
            if (Psg != null) {
                Log.d("PSG", "Ga Null")
                val psgArray = Psg as ArrayList<PassengerListEntity>
                adapterPsgList.setData(psgArray)
            } else Log.d("PSG", "Null")
        })
    }
}