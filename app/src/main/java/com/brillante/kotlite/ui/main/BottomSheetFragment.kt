package com.brillante.kotlite.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brillante.kotlite.databinding.BottomsheetFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class BottomSheetFragment: BottomSheetDialogFragment() {
    private var _binding: BottomsheetFragmentBinding? = null
    private val binding get() = _binding as BottomsheetFragmentBinding

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

        binding.btnDate.setOnClickListener { openDatePicker() }

        binding.btnTime.setOnClickListener { openTimePicker() }
    }

    private fun openDatePicker() {

        val today = MaterialDatePicker.todayInUtcMilliseconds()

        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Set Pickup Date")
            .setSelection(today)
            .build()
        picker.show(childFragmentManager, "SCHEDULE DATE")

        picker.addOnPositiveButtonClickListener {
            binding.tvDate.text = picker.headerText.toString()
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
            binding.tvTime.text = "${h}:${m}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}