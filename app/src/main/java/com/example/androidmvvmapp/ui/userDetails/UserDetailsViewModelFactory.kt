package com.example.androidmvvmapp.ui.userDetails

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Custom UserDetailsViewModelFactory class that passes shared preferences as a parameter to factory class
 */
class UserDetailsViewModelFactory(private val sharedPreferences: SharedPreferences) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserDetailsViewModel(sharedPreferences) as T
    }
}