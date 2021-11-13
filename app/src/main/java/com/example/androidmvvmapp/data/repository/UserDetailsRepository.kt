package com.example.androidmvvmapp.data.repository

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.example.androidmvvmapp.data.model.User
import com.example.androidmvvmapp.data.preference.PreferenceHelper
import com.google.firebase.auth.FirebaseAuth

/**
 * Repository class that fetches user details
 */
class UserDetailsRepository(private val sharedPreferences: SharedPreferences) {
    /**
     * Stores reference of the FirebaseAuth
     */
    private var auth: FirebaseAuth? = null

    /**
     * Stores mutable live data of user that contains user details
     */
    private val userDetailsLiveData = MutableLiveData<User>()

    /**
     * Stores mutable live data of boolean value that shows whether the user is logged out or not
     */
    private val userLogoutLiveData = MutableLiveData<Boolean>()

    init {
        //Initialising firebase auth
        auth = FirebaseAuth.getInstance()
    }

    /**
     * Updates user details in the userDetailsLiveData
     */
    fun getUserDetails() {
        val user = PreferenceHelper(sharedPreferences).getUserProfileDetails()
        userDetailsLiveData.postValue(user)
    }

    /**
     * Returns reference of userDetailsLiveData
     */
    fun getUserDetailsLiveData(): MutableLiveData<User> {
        return userDetailsLiveData
    }

    /**
     * Logouts the user and updates userLogoutLiveData
     */
    fun logout() {
        auth?.signOut()
        userLogoutLiveData.postValue(true)
    }

    /**
     * Returns reference of userLogoutLiveData
     */
    fun getLogoutLiveData(): MutableLiveData<Boolean> {
        return userLogoutLiveData
    }
}