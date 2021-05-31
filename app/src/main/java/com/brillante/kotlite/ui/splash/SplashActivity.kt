package com.brillante.kotlite.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.brillante.kotlite.R
import com.brillante.kotlite.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    private val splashTimeout: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setContentView(R.layout.activity_splash)

        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, splashTimeout)

    }
}