package com.brillante.kotlite.ui.driver.ongoing.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brillante.kotlite.data.local.entity.PassengerListEntity
import com.brillante.kotlite.databinding.ItemPsgOnGoingBinding
import com.brillante.kotlite.ui.driver.psgList.adapter.PsgListAdapter

class OnGoingAdapter : RecyclerView.Adapter<OnGoingViewHolder>() {
    private var onItemCLickCallback: OnClickCallback? = null

    val dataList = ArrayList<PassengerListEntity>()

    fun setData(data: ArrayList<PassengerListEntity>) {
        dataList.clear()
        dataList.addAll(data)
        Log.d("DATA", data.toString())
        notifyDataSetChanged()
    }

    fun setOnItemCLickCallback(onItemClickCallback: OnClickCallback) {
        this.onItemCLickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnGoingViewHolder {
        return OnGoingViewHolder(
            ItemPsgOnGoingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: OnGoingViewHolder, position: Int) {
        holder.bind(dataList[position])

        holder.btnArrived.setOnClickListener {
            onItemCLickCallback?.onArrivedClicked(dataList[position], holder)

        }

        holder.btnCompleteRide.setOnClickListener {
            onItemCLickCallback?.onCompleteRideClicked(dataList[position], holder)
        }

        holder.btnStartRide.setOnClickListener {
            onItemCLickCallback?.onStartRideClicked(dataList[position], holder)
        }

        holder.btnDone.setOnClickListener {
            onItemCLickCallback?.onDoneClicked(dataList[position], holder)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    interface OnClickCallback {
        fun onArrivedClicked(data: PassengerListEntity, holder: OnGoingViewHolder)
        fun onStartRideClicked(data: PassengerListEntity, holder: OnGoingViewHolder)
        fun onCompleteRideClicked(data: PassengerListEntity, holder: OnGoingViewHolder)
        fun onDoneClicked(data: PassengerListEntity, holder: OnGoingViewHolder)
    }
}