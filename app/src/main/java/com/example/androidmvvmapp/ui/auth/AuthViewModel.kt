package com.example.androidmvvmapp.ui.auth


import androidx.lifecycle.ViewModel
import com.example.androidmvvmapp.data.repository.UserRepository

class AuthViewModel : ViewModel() {
    private val userRepository = UserRepository()

    suspend fun userLogin(email: String, password: String) = userRepository.login(email, password)
    suspend fun userRegister(email: String, password: String) = userRepository.register(email, password)
}
