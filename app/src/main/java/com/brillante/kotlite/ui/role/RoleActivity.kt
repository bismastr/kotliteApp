package com.brillante.kotlite.ui.role

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brillante.kotlite.databinding.ActivityRoleBinding
import com.brillante.kotlite.ui.main.MapsActivity

class RoleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cvDriver.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }
}