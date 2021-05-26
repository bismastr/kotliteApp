package com.brillante.kotlite.ui.driver.psgList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brillante.kotlite.databinding.ActivityPassengerListBinding

class PassengerList : AppCompatActivity() {
    private lateinit var binding: ActivityPassengerListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPassengerListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //fragment pending list
        val pendingListFragment = PendingListFragment()
        binding.btnPendingList.setOnClickListener {
            pendingListFragment.show(supportFragmentManager, "PendingList")
        }
    }
}