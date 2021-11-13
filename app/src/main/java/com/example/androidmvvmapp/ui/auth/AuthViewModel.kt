package com.example.androidmvvmapp.ui.auth


import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidmvvmapp.data.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class AuthViewModel(sharedPreferences: SharedPreferences) : ViewModel() {
    /**
     * Stores mutable live data representing login status
     */
    private val loginStatusLiveData: MutableLiveData<Boolean>

    /**
     * Stores reference of user repository
     */
    private val userRepository: UserRepository = UserRepository(sharedPreferences)

    init {
        //Initialise loginStatusLiveData
        loginStatusLiveData = userRepository.getLoginStatusLiveData()
    }

    /**
     * Logins user
     */
    fun userLogin(email: String, password: String) {
        userRepository.login(email, password)
    }

    /**
     * Registers user
     */
    fun userRegister(name: String, lName: String, email: String, password: String) {
        userRepository.register(name, lName, email, password)
    }


    /**
     * Returns reference of loginStatusLiveData
     */
    fun getLoginStatusLiveData(): MutableLiveData<Boolean> {
        return loginStatusLiveData
    }
}
