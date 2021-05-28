package com.brillante.kotlite.ui.driver.psgList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brillante.kotlite.data.local.entity.PassengerListEntity
import com.brillante.kotlite.databinding.ActivityPassengerListBinding
import com.brillante.kotlite.ui.driver.ongoing.DriverOnGoingActivity
import com.brillante.kotlite.ui.driver.psgList.adapter.PsgListAdapter
import com.brillante.kotlite.viewmodel.ViewModelFactory

class PassengerListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPassengerListBinding

    private lateinit var passengerListViewModel: PassengerListViewModel
    private lateinit var adapterPsgList: PsgListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPassengerListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //fragment pending list
        val pendingListFragment = PendingListFragment()
        binding.btnPendingList.setOnClickListener {
            pendingListFragment.show(supportFragmentManager, "PendingList")
        }

        binding.btnRide.setOnClickListener {
            val intent = Intent(this, DriverOnGoingActivity::class.java)
            startActivity(intent)
        }

        //viewmodel
        val factory = ViewModelFactory.getInstance()
        passengerListViewModel =
            ViewModelProvider(this, factory)[PassengerListViewModel::class.java]

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapterPsgList = PsgListAdapter()
        binding.rvAcceptedList.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvAcceptedList.adapter = adapterPsgList
        getData()
    }


    private fun getData() {
        passengerListViewModel.getAccListPsg().observe(this, { Psg ->
            if (Psg != null) {
                Log.d("PSG", "Ga Null")
                val psgArray = Psg as ArrayList<PassengerListEntity>
                adapterPsgList.setData(psgArray)
            } else Log.d("PSG", "Null")
        })
    }
}