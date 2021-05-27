package com.brillante.kotlite.ui.driver.psgList.adapter

import androidx.recyclerview.widget.RecyclerView
import com.brillante.kotlite.data.local.entity.PassengerListEntity
import com.brillante.kotlite.databinding.ItemPsgBinding

class PsgListViewHolder(private val binding: ItemPsgBinding) : RecyclerView.ViewHolder(binding.root) {
    val accBtn = binding.btnAccepted
    val accDenied = binding.btnDenied
    fun bind(element: PassengerListEntity){
        binding.tvPsgDate.text = element.time
        binding.tvPsgName.text = element.firstName
    }
}