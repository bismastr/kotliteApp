package com.brillante.kotlite.ui.driver.ongoing.adapter

import androidx.recyclerview.widget.RecyclerView
import com.brillante.kotlite.data.local.entity.PassengerListEntity
import com.brillante.kotlite.databinding.ItemPsgOnGoingBinding

class OnGoingViewHolder(private val binding: ItemPsgOnGoingBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val btnArrived = binding.btnArrived
    val btnStartRide = binding.btnStartRide
    val btnCompleteRide = binding.btnCompleteRide
    val btnDone = binding.btnDone
    val btnDirection = binding.btnDirection
    fun bind(element: PassengerListEntity) {
        binding.tvPsgDate.text = element.time
        binding.tvPsgName.text = element.firstName
    }

}