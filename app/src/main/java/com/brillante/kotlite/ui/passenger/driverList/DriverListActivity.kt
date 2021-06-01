package com.brillante.kotlite.ui.passenger.driverList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brillante.kotlite.data.local.entity.PsgDataEntity
import com.brillante.kotlite.data.local.entity.RecommendationsItemEntity
import com.brillante.kotlite.data.remote.model.recomendation.RecommendationRequest
import com.brillante.kotlite.databinding.ActivityDriverListBinding
import com.brillante.kotlite.preferences.SessionManager
import com.brillante.kotlite.ui.passenger.driverList.adapter.DriverListAdapter
import com.brillante.kotlite.ui.passenger.ongoing.PassengerOnGoingActivity
import com.brillante.kotlite.viewmodel.ViewModelFactory

class DriverListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDriverListBinding

    private lateinit var driverListViewModel: DriverListViewModel
    private lateinit var driverListAdapter: DriverListAdapter
    private lateinit var psgDataEntity: PsgDataEntity
    private lateinit var sessionManager: SessionManager

    private lateinit var authHeader: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)
        authHeader = sessionManager.fetchAuthToken().toString()

        val driverRequest =
            intent.getParcelableExtra<RecommendationRequest>("extra_recommendation") as RecommendationRequest
        //viewmodel
        val factory = ViewModelFactory.getInstance()
        driverListViewModel = ViewModelProvider(this, factory)[DriverListViewModel::class.java]
        setupRecyclerView(driverRequest)
        setOnClickCallback()

    }

    private fun setOnClickCallback() {
        driverListAdapter.setOnItemCLickCallback(object : DriverListAdapter.OnClickCallback {
            override fun onChooseClicked(data: RecommendationsItemEntity) {
                driverListViewModel.createPsg(psgDataEntity, authHeader, data.id).observe(this@DriverListActivity, {Psg ->
                    if(Psg != null){
                        val intent = Intent(this@DriverListActivity, PassengerOnGoingActivity::class.java)
                        intent.putExtra("PSG", Psg)
                        startActivity(intent)
                    }
                })
            }

        })
    }

    private fun setupRecyclerView(request: RecommendationRequest) {
        driverListAdapter = DriverListAdapter()
        binding.rvDriverList.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvDriverList.adapter = driverListAdapter
        getData(request)

    }

    private fun getData(request: RecommendationRequest) {

        driverListViewModel.getDriverList(request, authHeader).observe(this, { Driver ->
            if (Driver != null) {
                val driverArray = Driver.recommendations as ArrayList
                psgDataEntity = Driver.psgData
                driverListAdapter.setData(driverArray)
            } else Log.d("PSG", "Null")
        })
    }
}