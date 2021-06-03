package com.brillante.kotlite.ui.driver.ongoing

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brillante.kotlite.R
import com.brillante.kotlite.data.local.entity.OrderEntity
import com.brillante.kotlite.data.local.entity.PassengerListEntity
import com.brillante.kotlite.databinding.ActivityDriverOnGoingBinding
import com.brillante.kotlite.preferences.SessionManager
import com.brillante.kotlite.ui.driver.invoce.DriverInvoiceActivity
import com.brillante.kotlite.ui.driver.ongoing.adapter.OnGoingAdapter
import com.brillante.kotlite.ui.driver.ongoing.adapter.OnGoingViewHolder
import com.brillante.kotlite.ui.driver.psgList.PassengerListViewModel
import com.brillante.kotlite.viewmodel.ViewModelFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior

class DriverOnGoingActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var binding: ActivityDriverOnGoingBinding
    private var orderId: Int = 0
    private lateinit var onGoingViewModel: PassengerOnGoingViewModel
    private lateinit var passengerListViewModel: PassengerListViewModel
    private lateinit var adapterOnGoing: OnGoingAdapter

    private lateinit var sessionManager: SessionManager
    private lateinit var authHeader: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverOnGoingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //map fragment
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_on_going) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        //sessionmanager
        sessionManager = SessionManager(this)
        authHeader = sessionManager.fetchAuthToken().toString()
        //getIntent
        val order = intent.getParcelableExtra<OrderEntity>("ORDER")
        if (order != null) {
            orderId = order.id
        }
        //btnClicklistener
        binding.btnFinishRide.setOnClickListener {
            val intent = Intent(this, DriverInvoiceActivity::class.java)
            intent.putExtra("ORDER", order)
            startActivity(intent)
        }
        val factory = ViewModelFactory.getInstance()
        onGoingViewModel = ViewModelProvider(this, factory)[PassengerOnGoingViewModel::class.java]
        passengerListViewModel =
            ViewModelProvider(this, factory)[PassengerListViewModel::class.java]

        val bottomSheet: ConstraintLayout = findViewById(R.id.statusBottomSheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.isHideable = false

        setupRecyclerView()
        onItemClickCallback()
    }

    private fun setupRecyclerView() {
        adapterOnGoing = OnGoingAdapter()
        binding.rvOngoingPsg.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvOngoingPsg.adapter = adapterOnGoing
        getData()
    }

    private fun getData() {
        passengerListViewModel.getAccListPsg(orderId, authHeader).observe(this, { Psg ->
            if (Psg != null) {
                Log.d("PSG", "Ga Null")
                val psgArray = Psg as ArrayList<PassengerListEntity>
                adapterOnGoing.setData(psgArray)
            } else Log.d("PSG", "Null")
        })
    }

    private fun onItemClickCallback() {
        adapterOnGoing.setOnItemCLickCallback(object : OnGoingAdapter.OnClickCallback {
            override fun onArrivedClicked(data: PassengerListEntity, holder: OnGoingViewHolder) {
                onGoingViewModel.patchArrived(data.id, authHeader)
                    .observe(this@DriverOnGoingActivity, { Success ->
                        if (Success) {
                            holder.btnArrived.visibility = View.GONE
                            holder.btnStartRide.visibility = View.VISIBLE
                        } else {
                            Toast.makeText(
                                this@DriverOnGoingActivity,
                                "ARRIVED FAILED",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })

            }

            override fun onStartRideClicked(data: PassengerListEntity, holder: OnGoingViewHolder) {
                onGoingViewModel.patchStart(data.id, authHeader)
                    .observe(this@DriverOnGoingActivity, { Success ->
                        if (Success) {
                            holder.btnStartRide.visibility = View.GONE
                            holder.btnCompleteRide.visibility = View.VISIBLE
                        } else {
                            Toast.makeText(
                                this@DriverOnGoingActivity,
                                "START FAILED",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })

            }

            override fun onCompleteRideClicked(
                data: PassengerListEntity,
                holder: OnGoingViewHolder
            ) {
                onGoingViewModel.patchComplete(data.id, authHeader)
                    .observe(this@DriverOnGoingActivity, { Success ->
                        if (Success) {
                            holder.btnCompleteRide.visibility = View.GONE
                            holder.btnDone.visibility = View.VISIBLE
                        } else {
                            Toast.makeText(
                                this@DriverOnGoingActivity,
                                "START FAILED",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })

            }

            override fun onDoneClicked(data: PassengerListEntity, holder: OnGoingViewHolder) {
                onGoingViewModel.patchDone(data.id, authHeader)
                    .observe(this@DriverOnGoingActivity, { Success ->
                        if (Success) {
                            Toast.makeText(
                                this@DriverOnGoingActivity,
                                "Done CLICKED",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@DriverOnGoingActivity,
                                "START FAILED",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }

            override fun onDirectionClicked(data: PassengerListEntity, holder: OnGoingViewHolder) {
                val psgPickupLocation = data.latPick + "," + data.longPick
                val gmmIntentUri =
                    Uri.parse("google.navigation:q=$psgPickupLocation")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }

        })

    }

    override fun onMapReady(p0: GoogleMap) {
        getRoute(p0)
    }

    private fun getRoute(map: GoogleMap) {
        onGoingViewModel.getRoute(orderId, authHeader, map)
    }
}