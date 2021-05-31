package com.brillante.kotlite.ui.driver.psgList.adapter

import androidx.recyclerview.widget.RecyclerView
import com.brillante.kotlite.data.local.entity.PassengerListEntity
import com.brillante.kotlite.databinding.ItemPsgAccBinding

class PsgListAccViewHolder(private val binding: ItemPsgAccBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val btnCall = binding.btnCall

    fun bind(element: PassengerListEntity) {
        val distance = element.distance/200
        val fee = element.fee

        binding.tvFirstname.text = element.firstName
        binding.tvDistance.text = "$distance Km"
        binding.tvDrop.text = element.placeDrop
        binding.tvPickup.text = element.placePick
        binding.tvFee.text = "Rp $fee "
    }
}