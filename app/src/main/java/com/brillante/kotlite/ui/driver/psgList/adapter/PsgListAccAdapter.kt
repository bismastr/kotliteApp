package com.brillante.kotlite.ui.driver.psgList.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brillante.kotlite.data.local.entity.PassengerListEntity
import com.brillante.kotlite.databinding.ItemPsgAccBinding

class PsgListAccAdapter: RecyclerView.Adapter<PsgListAccViewHolder>() {
    private var onItemCLickCallback: PsgListAdapter.OnClickCallback? = null

    val dataList = ArrayList<PassengerListEntity>()

    fun setData(data: ArrayList<PassengerListEntity>){
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PsgListAccViewHolder {
        return PsgListAccViewHolder(
            ItemPsgAccBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PsgListAccViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}