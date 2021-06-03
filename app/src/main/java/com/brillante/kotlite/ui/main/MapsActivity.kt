package com.brillante.kotlite.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.brillante.kotlite.BuildConfig
import com.brillante.kotlite.R
import com.brillante.kotlite.databinding.ActivityMapsBinding
import com.brillante.kotlite.ui.MapViewModel
import com.brillante.kotlite.viewmodel.ViewModelFactory
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnCameraIdleListener {
    private var map: GoogleMap? = null
    private var isCanceled: Boolean = true
    private lateinit var binding: ActivityMapsBinding



    // The entry point to the Fused Location Provider.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // A default location
    // not granted.
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var locationPermissionGranted = false

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private var lastKnownLocation: Location? = null
    private var destinationLocation: LatLng? = null
    private var pickupLocation: LatLng? = null

    //ViewModel
    private lateinit var mapViewModel: MapViewModel

    companion object {
        private val TAG = MapsActivity::class.java.simpleName
        private const val DEFAULT_ZOOM = 20
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        const val EXTRA_CAPACITY = "extra_capacity"
        const val EXTRA_CARTYPE = "extra_cartype"
        const val EXTRA_FROM = "extra_from"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //ViewModel
        val factory = ViewModelFactory.getInstance()
        mapViewModel = ViewModelProvider(this, factory)[MapViewModel::class.java]
        //getIntentData
        val carCapacity: String = intent.getStringExtra(EXTRA_CAPACITY).toString()
        val carType: String = intent.getStringExtra(EXTRA_CARTYPE).toString()
        val from: Int = intent.getIntExtra(EXTRA_FROM, 0)
        //bottomsheet
        var bottomSheetFragment: TimePickerFragment
        binding.btnSchedule.setOnClickListener {
            bottomSheetFragment =
                TimePickerFragment().apply { inject(pickupLocation, destinationLocation, carType, carCapacity, from) }
            bottomSheetFragment.show(supportFragmentManager, "ScheduleBottomSheet")
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        // Initialize the SDK
        Places.initialize(applicationContext, BuildConfig.API_KEY)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.map = googleMap
        getLocationPermission()
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI()
        // Get the current location of the device and set the position of the map.
        getDeviceLocation()

        autoComplete()
    }

    private fun autoComplete() {
        if (locationPermissionGranted) {
            val autocompleteFragment =
                supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                        as AutocompleteSupportFragment
            autocompleteFragment.setHint("Mau Kemana Hari Ini?")
            autocompleteFragment.setTypeFilter(TypeFilter.ESTABLISHMENT)
            autocompleteFragment.setCountries("ID")
            // Specify the types of place data to return.
            autocompleteFragment.setPlaceFields(
                listOf(
                    Place.Field.ID,
                    Place.Field.NAME,
                    Place.Field.LAT_LNG
                )
            )

            // Set up a PlaceSelectionListener to handle the response.
            autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
                override fun onPlaceSelected(place: Place) {
                    destinationLocation = place.latLng
                    Log.i("TAG", "Place: ${place.name}, ${place.id}")
                    isCanceled = false
                    getDeviceLocation()
                    onCameraIdle()

                }

                override fun onError(status: Status) {
                    // TODO: Handle the error.
                    Log.i("TAG", "An error occurred: $status")
                }
            })

        }
    }

    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
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

                // If request is cancelled, the result arrays are empty.
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
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
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

    private fun showDirection(from: LatLng?, to: LatLng?, map: GoogleMap?) {
        if (map !== null) {
            val fromString = from?.latitude.toString() + "," + from?.longitude.toString()
            val toString = to?.latitude.toString() + "," + to?.longitude.toString()
            mapViewModel.getDirection(fromString, toString, map)

        }

    }

    override fun onCameraIdle() {
        val center: LatLng? = map?.cameraPosition?.target

        if (!isCanceled) {

            map?.setOnCameraIdleListener(this@MapsActivity)
            Toast.makeText(applicationContext, center.toString(), Toast.LENGTH_LONG).show()
            binding.imgMarker.visibility = View.VISIBLE
            binding.btnSetlocation.visibility = View.VISIBLE

        } else return

        binding.btnSetlocation.setOnClickListener {
            isCanceled = true
            binding.imgMarker.visibility = View.GONE
            binding.btnSetlocation.visibility = View.GONE
            pickupLocation = center
            showDirection(pickupLocation, destinationLocation, map)
        }


    }
}