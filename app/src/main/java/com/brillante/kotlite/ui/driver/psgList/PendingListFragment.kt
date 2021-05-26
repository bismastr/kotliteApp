package com.brillante.kotlite.ui.driver.psgList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brillante.kotlite.data.local.entity.PassengerListEntity
import com.brillante.kotlite.databinding.FragmentPendingListBinding
import com.brillante.kotlite.ui.driver.psgList.adapter.PsgListAdapter
import com.brillante.kotlite.viewmodel.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PendingListFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentPendingListBinding? = null
    private val binding get() = _binding as FragmentPendingListBinding

    private lateinit var passengerListViewModel: PassengerListViewModel
    private lateinit var adapterPsgList: PsgListAdapter

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
        //viewmodel
        val factory = ViewModelFactory.getInstance()
        passengerListViewModel =
            ViewModelProvider(this, factory)[PassengerListViewModel::class.java]
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapterPsgList = PsgListAdapter()
        binding.rvPendingList.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvPendingList.adapter = adapterPsgList
        getPsgList()
    }

    private fun getPsgList() {
        passengerListViewModel.getPsgList().observe(viewLifecycleOwner, { Psg ->
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