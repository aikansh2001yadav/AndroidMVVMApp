package com.example.androidmvvmapp.ui.userDetails

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidmvvmapp.data.model.User
import com.example.androidmvvmapp.data.repository.UserDetailsRepository

class UserDetailsViewModel(sharedPreferences: SharedPreferences) : ViewModel() {

    /**
     * Stores reference of userLogoutLiveData that contains user logout status
     */
    private val userLogoutLiveData: MutableLiveData<Boolean>

    /**
     * Stores reference of userDetailsLiveData that contains user details
     */
    private val userDetailsLiveData: MutableLiveData<User>
    private val userDetailsRepository = UserDetailsRepository(sharedPreferences)

    init {
        userDetailsLiveData = userDetailsRepository.getUserDetailsLiveData()
        userLogoutLiveData = userDetailsRepository.getLogoutLiveData()
    }

    /**
     * Logouts user
     */
    fun userLogout() {
        userDetailsRepository.logout()
    }

    /**
     * Updates user details of the current user
     */
    fun getUserDetails() {
        userDetailsRepository.getUserDetails()
    }

    /**
     * Returns reference of the userDetailsLiveData
     */
    fun getUserDetailsLiveData(): MutableLiveData<User> {
        return userDetailsLiveData
    }

    /**
     * Returns reference of the userLogoutLiveData
     */
    fun getUserLogoutLiveData(): MutableLiveData<Boolean> {
        return userLogoutLiveData
    }
}