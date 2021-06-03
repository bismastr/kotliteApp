package com.brillante.kotlite.ui.passenger.ongoing

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.brillante.kotlite.R
import com.brillante.kotlite.data.local.entity.CreatePsgEntity
import com.brillante.kotlite.data.local.entity.DetailDriverEntity
import com.brillante.kotlite.databinding.ActivityPassengerOnGoingBinding
import com.brillante.kotlite.preferences.SessionManager
import com.brillante.kotlite.ui.MapViewModel
import com.brillante.kotlite.ui.driver.invoce.DriverInvoiceViewModel
import com.brillante.kotlite.ui.main.MapsActivity
import com.brillante.kotlite.viewmodel.ViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.*
import java.util.*

class PassengerOnGoingActivity : AppCompatActivity(), OnMapReadyCallback {
    //map
    private var map: GoogleMap? = null

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var binding: ActivityPassengerOnGoingBinding
    private var lastKnownLocation: Location? = null
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var locationPermissionGranted = false

    private lateinit var driverInvoiceViewModel: DriverInvoiceViewModel
    private lateinit var passengerOnGoingViewModel: PsgOnGoingViewModel
    private lateinit var mapViewModel: MapViewModel
    private var psgId = 10
    private lateinit var dataPsg: CreatePsgEntity

    private var isArrivingShow = false
    private var isArrivedShow = false
    private var isComplete = false
    private var isStart = false

    private lateinit var sessionManager: SessionManager
    private lateinit var authHeader: String

    companion object {
        private val TAG = MapsActivity::class.java.simpleName
        private const val DEFAULT_ZOOM = 20
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPassengerOnGoingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //map
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_passenger_ongoing) as SupportMapFragment
        mapFragment.getMapAsync(this)
        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        //sessionmanager
        sessionManager = SessionManager(this)
        authHeader = sessionManager.fetchAuthToken().toString()
        //factory
        val factory = ViewModelFactory.getInstance()
        driverInvoiceViewModel =
            ViewModelProvider(this, factory)[DriverInvoiceViewModel::class.java]
        passengerOnGoingViewModel =
            ViewModelProvider(this, factory)[PsgOnGoingViewModel::class.java]
        mapViewModel = ViewModelProvider(this, factory)[MapViewModel::class.java]
        val psgData = intent.getParcelableExtra<CreatePsgEntity>("PSG")
        if (psgData != null) {
            psgId = psgData.id
            dataPsg = psgData
        }
        getData(psgData, authHeader)

        GlobalScope.launch {
            repeatDetail()
        }

    }

    private fun showDirection(map: GoogleMap?) {
        val fromString = dataPsg.latPick + "," + dataPsg.longPick
        val toString = dataPsg.latDrop + "," + dataPsg.longDrop
        if (map != null) {
            mapViewModel.getDirection(fromString, toString, map)
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
        DONE("Done"),
    }

    private fun displayDialog(status: PsgStatus) {
        when (status) {
            PsgStatus.PENDING -> Log.d("PENDING", "PENDING")
            PsgStatus.ARRIVING -> dialogArriving()
            PsgStatus.ARRIVED -> dialogArrived(isArrivedShow)
            PsgStatus.COMPLETERIDE -> dialogCompleteRide()
            PsgStatus.STARTRIDE -> dialogStartRide()
            PsgStatus.DONE -> dialogDone()
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

    override fun onMapReady(p0: GoogleMap) {
        this.map = p0
        getLocationPermission()
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI()
        // Get the current location of the device and set the position of the map.
        getDeviceLocation()

        showDirection(map)

    }

    private fun getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            map?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude
                                    ), DEFAULT_ZOOM.toFloat()
                                )
                            )
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        map?.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                        )
                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    private fun updateLocationUI() {
        if (map == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

}