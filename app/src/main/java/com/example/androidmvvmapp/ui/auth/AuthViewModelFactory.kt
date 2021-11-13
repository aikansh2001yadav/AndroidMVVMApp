package com.example.androidmvvmapp.ui.auth

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Custom AuthViewModelFactory class that passes shared preferences as a parameter to factory class
 */
class AuthViewModelFactory(private val sharedPreferences: SharedPreferences) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(sharedPreferences) as T
    }
}