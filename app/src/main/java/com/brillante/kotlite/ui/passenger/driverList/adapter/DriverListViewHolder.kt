package com.brillante.kotlite.ui.passenger.driverList.adapter

import androidx.recyclerview.widget.RecyclerView
import com.brillante.kotlite.data.local.entity.RecommendationsItemEntity
import com.brillante.kotlite.databinding.ItemDriverBinding

class DriverListViewHolder(private val binding: ItemDriverBinding): RecyclerView.ViewHolder(binding.root) {

    val btnChoose = binding.btnChoose

    fun bind(element: RecommendationsItemEntity){
        val capacity = element.totalPsg
        val totalPsg = element.capacity
        val totalCapacity = "$capacity / $totalPsg"

        binding.tvCapacity.text = totalCapacity
        binding.tvCarType.text = element.carType
        binding.tvFirstname.text = element.firstName
        binding.tvPickup.text = element.placeStart
        binding.tvDrop.text = element.placeEnd
    }

}