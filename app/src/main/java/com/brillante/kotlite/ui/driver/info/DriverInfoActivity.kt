package com.brillante.kotlite.ui.driver.info

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.brillante.kotlite.R
import com.brillante.kotlite.databinding.ActivityDriverInfoBinding
import com.brillante.kotlite.databinding.ActivityRoleBinding
import com.brillante.kotlite.ui.main.MapsActivity

class DriverInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDriverInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSelesai.setOnClickListener{
            sendDriverInfo()
        }

    }

    private fun sendDriverInfo() {
        val capacity = binding.edtTextCarCapacity.text.toString().trim() {it <= ' '}
        val carType = binding.edtTextCarType.text.toString().trim { it <= ' ' }

        if (capacity.isEmpty()) {
            binding.edtTextCarCapacity.error = "Capacity is Required"
            binding.edtTextCarCapacity.requestFocus()
            Toast.makeText(
                this,
                "Masukan Kapasitas Mobil Terlebih Dahulu",
                Toast.LENGTH_SHORT
            ).show()
        }

        if (carType.isEmpty()) {
            binding.edtTextCarType.error = "CarType is Required"
            binding.edtTextCarType.requestFocus()
            Toast.makeText(
                this,
                "Masukan Jenis Mobilmu Terlebih Dahulu",
                Toast.LENGTH_SHORT
            ).show()
        }

        val  intent = Intent(this, MapsActivity::class.java)
        intent.putExtra(MapsActivity.EXTRA_CAPACITY, capacity)
        intent.putExtra( MapsActivity.EXTRA_CARTYPE, carType)
        startActivity(intent)
    }
}