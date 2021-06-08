package com.brillante.kotlite.ui.driver.psgList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brillante.kotlite.data.local.entity.PassengerListEntity
import com.brillante.kotlite.databinding.FragmentPendingListBinding
import com.brillante.kotlite.preferences.SessionManager
import com.brillante.kotlite.ui.driver.psgList.adapter.PsgListAdapter
import com.brillante.kotlite.viewmodel.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PendingListFragment : BottomSheetDialogFragment() {
    private var orderId: Int = 0
    private var onStatusPatch: ((Status) -> Unit)? = null
    private var _binding: FragmentPendingListBinding? = null
    private val binding get() = _binding as FragmentPendingListBinding
    private lateinit var passengerListViewModel: PassengerListViewModel
    private lateinit var adapterPsgList: PsgListAdapter

    private lateinit var sessionManager: SessionManager
    private lateinit var authHeader: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPendingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //sessionmanager
        sessionManager = SessionManager(requireContext())
        authHeader = sessionManager.fetchAuthToken().toString()
        //viewmodel
        val factory = ViewModelFactory.getInstance()
        passengerListViewModel =
            ViewModelProvider(this, factory)[PassengerListViewModel::class.java]
        setupRecyclerView()
        setOnClickCallback()
    }

    fun inject(
        orderId: Int,
        onStatusPatch: ((Status) -> Unit)?
    ) {
        this.orderId = orderId
        this.onStatusPatch = onStatusPatch
    }

    private fun setupRecyclerView() {
        adapterPsgList = PsgListAdapter()
        binding.rvPendingList.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvPendingList.adapter = adapterPsgList
        getPsgList()
    }

    private fun setOnClickCallback() {
        adapterPsgList.setOnItemCLickCallback(object : PsgListAdapter.OnClickCallback {
            override fun onAccClicked(data: PassengerListEntity, position: Int) {
                passengerListViewModel.patchAccPsg(data.id, authHeader).observe(viewLifecycleOwner, {Patch ->
                    if (Patch != false){
                        adapterPsgList.dataList.removeAt(position)
                        adapterPsgList.notifyItemRemoved(position)
                        adapterPsgList.notifyItemRangeChanged(position, adapterPsgList.dataList.size)
                        onStatusPatch?.invoke(Status.ACCEPTED)
                    }
                })

            }

            override fun onDeniedClicked(data: PassengerListEntity, position: Int) {
                adapterPsgList.dataList.removeAt(position)
                adapterPsgList.notifyItemRemoved(position)
                onStatusPatch?.invoke(Status.DENIED)
            }

        })

    }

    enum class Status {
        ACCEPTED, DENIED
    }

    private fun getPsgList() {
        passengerListViewModel.getPsgList(orderId, authHeader).observe(viewLifecycleOwner, { Psg ->
            if (Psg != null) {
                Log.d("PSG", "Ga Null")
                val psgArray = Psg as ArrayList<PassengerListEntity>
                adapterPsgList.setData(psgArray)
            } else Log.d("PSG", "Null")
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}