package com.brillante.kotlite.ui.driver.psgList.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brillante.kotlite.data.local.entity.PassengerListEntity
import com.brillante.kotlite.databinding.ItemPsgBinding

class PsgListAdapter: RecyclerView.Adapter<PsgListViewHolder>() {
    private var onItemCLickCallback: OnClickCallback? = null

    val dataList = ArrayList<PassengerListEntity>()

    fun setData(data: ArrayList<PassengerListEntity>){
        dataList.clear()
        dataList.addAll(data)
        Log.d("DATA", data.toString())
        notifyDataSetChanged()
    }

    fun setOnItemCLickCallback(onItemClickCallback: OnClickCallback) {
        this.onItemCLickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PsgListViewHolder {
        return PsgListViewHolder(
            ItemPsgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PsgListViewHolder, position: Int) {
        holder.bind(dataList[position])

        holder.accBtn.setOnClickListener {
            onItemCLickCallback?.onAccClicked(dataList[position], position)
        }

        holder.accDenied.setOnClickListener {
            onItemCLickCallback?.onDeniedClicked(dataList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
   interface OnClickCallback {
       fun onAccClicked(data: PassengerListEntity, position: Int)
       fun onDeniedClicked(data: PassengerListEntity, position: Int)
   }



}