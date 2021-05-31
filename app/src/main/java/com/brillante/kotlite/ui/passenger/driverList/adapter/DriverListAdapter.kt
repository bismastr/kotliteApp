package com.brillante.kotlite.ui.passenger.driverList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brillante.kotlite.data.local.entity.RecommendationsItemEntity
import com.brillante.kotlite.databinding.ItemDriverBinding

class DriverListAdapter : RecyclerView.Adapter<DriverListViewHolder>() {
    private var onItemCLickCallback: OnClickCallback? = null
    private val dataList = ArrayList<RecommendationsItemEntity>()

    fun setData(data: ArrayList<RecommendationsItemEntity>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemCLickCallback(onItemClickCallback: OnClickCallback) {
        this.onItemCLickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverListViewHolder {
        return DriverListViewHolder(
            ItemDriverBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DriverListViewHolder, position: Int) {
        holder.bind(dataList[position])

        holder.btnChoose.setOnClickListener {
            onItemCLickCallback?.onChooseClicked(dataList[position])
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    interface OnClickCallback {
        fun onChooseClicked(data: RecommendationsItemEntity)
    }


}