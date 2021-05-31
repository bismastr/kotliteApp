package com.brillante.kotlite.ui.driver.psgList.adapter

import androidx.recyclerview.widget.RecyclerView
import com.brillante.kotlite.data.local.entity.PassengerListEntity
import com.brillante.kotlite.databinding.ItemPsgBinding

class PsgListViewHolder(private val binding: ItemPsgBinding) : RecyclerView.ViewHolder(binding.root) {
    val accBtn = binding.btnAcc
    val accDenied = binding.btnDenied
    fun bind(element: PassengerListEntity){
        binding.tvDistance.text = (element.distance/1000).toString() + " Km"
        binding.tvFirstname.text = element.firstName
        binding.tvDrop.text = element.placeDrop
        binding.tvPickup.text = element.placePick
        binding.tvFee.text = "Rp.${element.fee}"
    }
}