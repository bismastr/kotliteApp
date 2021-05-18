package com.brillante.kotlite.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.brillante.kotlite.databinding.ActivityLoginBinding
import com.brillante.kotlite.preferences.SessionManager
import com.brillante.kotlite.ui.register.RegisterActivity
import com.brillante.kotlite.ui.role.RoleActivity
import com.brillante.kotlite.viewmodel.ViewModelFactory


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var sessionManager: SessionManager
    //viewModel
    private lateinit var loginViewModel: LoginViewModel

    companion object {
        private val TAG = LoginActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //viewmodel
        val factory = ViewModelFactory.getInstance()
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

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

        loginViewModel.loginUser(username, password, sessionManager, this).observe(this, {isSucces ->
            if (isSucces){
                startActivity(Intent(this@LoginActivity, RoleActivity::class.java))
            }
        })
    }
}