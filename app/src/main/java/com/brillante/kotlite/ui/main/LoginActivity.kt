package com.brillante.kotlite.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.brillante.kotlite.databinding.ActivityLoginBinding
import com.brillante.kotlite.model.LoginRequest
import com.brillante.kotlite.network.Retrofit
import com.brillante.kotlite.preferences.SessionManager
import com.brillante.kotlite.ui.responses.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var sessionManager: SessionManager

    companion object {
        private val TAG = LoginActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        sessionManager = SessionManager(this)

        binding.btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {

        val username = binding.etUsername.text.toString().trim() {it <= ' '}
        val password = binding.etPassword.text.toString().trim {it <= ' '}

        if (username.isEmpty()) {
            binding.etUsername.error = "Email Is Required"
            binding.etUsername.requestFocus()
            Toast.makeText(
                this@LoginActivity,
                "Please enter your Username",
                Toast.LENGTH_SHORT
            ). show()
        }

        if (password.isEmpty()) {
            binding.etPassword.error = "Password Is Required"
            binding.etPassword.requestFocus()
            Toast.makeText(
                this@LoginActivity,
                "Please enter your Password",
                Toast.LENGTH_SHORT
            ). show()
        }

        Retrofit.apiRequest.login(LoginRequest(username = username, password = password)).enqueue(object : Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                if (response.isSuccessful) {
                    response.body()?.let { sessionManager.saveAuthToken(it.access) }
                    startActivity(Intent(this@LoginActivity, RoleActivity::class.java))
                }
                else {
                    Log.d(TAG, response.code().toString())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(
                    this@LoginActivity,
                    "Failed To Login",
                    Toast.LENGTH_SHORT
                ). show()
            }

        })
    }
}