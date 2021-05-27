package com.brillante.kotlite.ui.role

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brillante.kotlite.databinding.ActivityRoleBinding
import com.brillante.kotlite.ui.driver.info.DriverInfoActivity
import com.brillante.kotlite.ui.main.MapsActivity

class RoleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cvDriver.setOnClickListener {
            startActivity(Intent(this, DriverInfoActivity::class.java))
        }

        binding.cvPassanger.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra(MapsActivity.EXTRA_FROM, 1)
            startActivity(intent)
        }
    }
}