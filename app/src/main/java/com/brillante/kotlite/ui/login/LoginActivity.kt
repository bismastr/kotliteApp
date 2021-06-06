package com.brillante.kotlite.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.brillante.kotlite.databinding.ActivityLoginBinding
import com.brillante.kotlite.preferences.SessionManager
import com.brillante.kotlite.ui.role.RoleActivity
import com.brillante.kotlite.viewmodel.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var sessionManager: SessionManager

    //viewModel
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //viewmodel
        val factory = ViewModelFactory.getInstance()
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        binding.tvRegister.setOnClickListener {
            dialog()
        }

        sessionManager = SessionManager(this)

        binding.btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {

        val username = binding.etUsername.text.toString().trim() { it <= ' ' }
        val password = binding.etPassword.text.toString().trim { it <= ' ' }

        if (username.isEmpty()) {
            binding.etUsername.error = "Email Is Required"
            binding.etUsername.requestFocus()
            Toast.makeText(
                this@LoginActivity,
                "Please enter your Username",
                Toast.LENGTH_SHORT
            ).show()
        }

        if (password.isEmpty()) {
            binding.etPassword.error = "Password Is Required"
            binding.etPassword.requestFocus()
            Toast.makeText(
                this@LoginActivity,
                "Please enter your Password",
                Toast.LENGTH_SHORT
            ).show()
        }

        loginViewModel.loginUser(username, password, sessionManager, this)
            .observe(this, { isSuccess ->
                if (isSuccess) {
                    startActivity(Intent(this@LoginActivity, RoleActivity::class.java))
                } else {
                    binding.etUsername.error = ""
                    binding.etPassword.error = ""
                    Toast.makeText(
                        this@LoginActivity,
                        "Failed To Login",
                        Toast.LENGTH_LONG
                    ).show()

                }

            })
    }

    private fun dialog() {
        val url = "http://www.kotlite.site"

        MaterialAlertDialogBuilder(this)
            .setTitle("Please Register In Kotlite Official Website")
            .setMessage("Because of limited services within our server, for now registering only available in Kotlite official website")
            .setPositiveButton("REGISTER NOW") { dialog, which ->
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
            .setNegativeButton("NOT NOW"){ dialog, which ->

            }
            .show()
    }
}
