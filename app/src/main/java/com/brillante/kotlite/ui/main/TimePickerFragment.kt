package com.brillante.kotlite.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.brillante.kotlite.databinding.BottomsheetFragmentBinding
import com.brillante.kotlite.preferences.SessionManager
import com.brillante.kotlite.ui.MapViewModel
import com.brillante.kotlite.ui.driver.psgList.PassengerList
import com.brillante.kotlite.viewmodel.ViewModelFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class TimePickerFragment(
    private val pickupLatLng: LatLng?,
    private val destinationLatLng: LatLng?,
    private val carType: String?,
    private val carCapacity: String?,

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
        Log.d("LATLONG", destinationLatLng.toString())
        binding.btnDate.setOnClickListener { openDatePicker() }

        binding.btnTime.setOnClickListener { openTimePicker() }

        binding.btnOrder.setOnClickListener {
            if (carCapacity != null && carType != null) {
                createOrder(carCapacity, carType)
            }
        }

        sessionManager = SessionManager(requireContext())
    }

    private fun openDatePicker() {

        val today = MaterialDatePicker.todayInUtcMilliseconds()

        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Set Pickup Date")
            .setSelection(today)
            .build()
        picker.show(childFragmentManager, "SCHEDULE DATE")

        picker.addOnPositiveButtonClickListener {
            datePickup = picker.headerText
            binding.tvDate.text = picker.headerText
        }

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
            timePickup = "${h}:${m}"
            binding.tvTime.text = "${h}:${m}"
        }
    }

    private fun createOrder(carCapacity: String, carType: String) {
        val token = sessionManager.fetchAuthToken()
        Log.d("TOKEN", token.toString())
        if (token != null) {
            mapViewModel.createOrder(
                pickupLatLng,
                destinationLatLng,
                "$timePickup + $datePickup",
                carCapacity.toInt(),
                carType,
                requireContext(),
                token.toString()
            ).observe(viewLifecycleOwner, { isSuccess ->
                if (isSuccess) {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                    val intent = Intent(activity, PassengerList::class.java)
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