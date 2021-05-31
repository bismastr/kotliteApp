package com.brillante.kotlite.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.brillante.kotlite.databinding.BottomsheetFragmentBinding
import com.brillante.kotlite.data.remote.model.recomendation.RecommendationRequest
import com.brillante.kotlite.preferences.SessionManager
import com.brillante.kotlite.ui.MapViewModel
import com.brillante.kotlite.ui.driver.psgList.PassengerListActivity
import com.brillante.kotlite.ui.passenger.driverList.DriverListActivity
import com.brillante.kotlite.viewmodel.ViewModelFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

class TimePickerFragment(
    private val pickupLatLng: LatLng?,
    private val destinationLatLng: LatLng?,
    private val carType: String,
    private val carCapacity: String,
    private val from: Int

) : BottomSheetDialogFragment() {
    private var _binding: BottomsheetFragmentBinding? = null
    private val binding get() = _binding as BottomsheetFragmentBinding

    private lateinit var mapViewModel: MapViewModel
    private lateinit var timePickup: String
    private lateinit var datePickup: String

    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomsheetFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ViewModel
        val factory = ViewModelFactory.getInstance()
        mapViewModel = ViewModelProvider(this, factory)[MapViewModel::class.java]
        binding.btnDate.setOnClickListener { openDatePicker() }

        binding.btnTime.setOnClickListener { openTimePicker() }

        binding.btnOrder.setOnClickListener {
            if (from == 0) {
                createOrder(carCapacity, carType)

            } else if(from == 1){
                createPsg()
            }

        }

        sessionManager = SessionManager(requireContext())
    }

    private fun createPsg() {
        val latPick = pickupLatLng?.latitude.toString()
        val longPick = pickupLatLng?.longitude.toString()
        val latDrop = destinationLatLng?.latitude.toString()
        val longDrop = destinationLatLng?.longitude.toString()

        val driverListRequest = RecommendationRequest(
            latPick,
            longPick,
            latDrop,
            longDrop,
            "$datePickup $timePickup",
        )
        val intent = Intent(activity, DriverListActivity::class.java)
        intent.putExtra("extra_recommendation", driverListRequest)
        activity?.startActivity(intent)
    }

    private fun openDatePicker() {

        val today = MaterialDatePicker.todayInUtcMilliseconds()

        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Set Pickup Date")
            .setSelection(today)
            .build()
        picker.show(childFragmentManager, "SCHEDULE DATE")


        picker.addOnPositiveButtonClickListener {
            val calendarDate = Calendar.getInstance()
            calendarDate.timeInMillis = picker.selection!!
            val month = getAbbreviatedFromDateTime(calendarDate,"MM")
            val day=getAbbreviatedFromDateTime(calendarDate,"d")
            val year=getAbbreviatedFromDateTime(calendarDate,"yyyy")
            datePickup = "$day-$month-$year"
            binding.tvDate.text = datePickup
        }

    }

    private fun getAbbreviatedFromDateTime(dateTime: Calendar, field: String): String? {
        val output = SimpleDateFormat(field)
        try {
            return output.format(dateTime.time)    // format output
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }


    private fun openTimePicker() {
        val clockFormat = TimeFormat.CLOCK_24H
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(7)
            .setMinute(0)
            .setTitleText("Set Pickup Time")
            .build()
        picker.show(childFragmentManager, "SCHEDULE TIME")

        picker.addOnPositiveButtonClickListener {

            val h = picker.hour
            val m = picker.minute
            val hourAsText = if (h < 10) "0$h" else h
            val minuteAsText = if (m < 10) "0$m" else m
            timePickup = "${hourAsText}:${minuteAsText}"
            binding.tvTime.text = timePickup
        }
    }

    private fun createOrder(carCapacity: String, carType: String) {
        val token = sessionManager.fetchAuthToken()

        if (token != null) {
            mapViewModel.createOrder(
                pickupLatLng,
                destinationLatLng,
                "$datePickup $timePickup",
                carCapacity.toInt(),
                carType,
                requireContext(),
                token.toString()
            ).observe(viewLifecycleOwner, { Order ->
                if (Order != null) {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                    val intent = Intent(activity, PassengerListActivity::class.java)
                    intent.putExtra("ORDER", Order)
                    activity?.startActivity(intent)

                } else
                    Toast.makeText(requireContext(), "Error Post", Toast.LENGTH_LONG).show()
            })
        } else
            Toast.makeText(
                requireContext(),
                "Failed To Login",
                Toast.LENGTH_LONG
            ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}