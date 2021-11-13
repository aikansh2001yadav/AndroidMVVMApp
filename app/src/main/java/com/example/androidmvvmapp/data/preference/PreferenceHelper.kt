package com.example.androidmvvmapp.data.preference

import android.content.SharedPreferences
import com.example.androidmvvmapp.data.model.User
import com.example.androidmvvmapp.utils.Constants

class PreferenceHelper(private val sharedPreferences: SharedPreferences) {
    /**
     * Saves user profile details via sharedPreferences,
     */
    fun saveUserProfileDetails(user: User) {
        val editor = sharedPreferences.edit()
        editor.putString(Constants.ID, user.id)
        editor.putString(Constants.NAME, user.name)
        editor.putString(Constants.LAST_NAME, user.lastName)
        editor.putString(Constants.EMAIL, user.email)
        editor.apply()
    }

    /**
     * Gets user profile details via sharedPreferences
     */
    fun getUserProfileDetails(): User {
        val id = sharedPreferences.getString(Constants.ID, "")
        val name = sharedPreferences.getString(Constants.NAME, "")
        val lName = sharedPreferences.getString(Constants.LAST_NAME, "")
        val email = sharedPreferences.getString(Constants.EMAIL, "")
        return User(id, name, lName, email)
    }
}