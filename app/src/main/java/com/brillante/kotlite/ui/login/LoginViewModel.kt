package com.brillante.kotlite.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.brillante.kotlite.data.Repository
import com.brillante.kotlite.preferences.SessionManager

class LoginViewModel(private val repository: Repository): ViewModel() {

    fun loginUser(username: String, password: String, sessionManager: SessionManager, context: Context): LiveData<Boolean> {
        return repository.login(username, password, sessionManager, context)
    }
}