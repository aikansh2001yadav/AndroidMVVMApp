package com.example.androidmvvmapp.ui.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.androidmvvmapp.R
import com.example.androidmvvmapp.UserDetailsActivity
import com.example.androidmvvmapp.databinding.ActivityLoginBinding
import com.example.androidmvvmapp.utils.hide
import com.example.androidmvvmapp.utils.show
import com.example.androidmvvmapp.utils.toast

class LoginActivity : AppCompatActivity() {

    /**
     * Stores reference of login progress bar
     */
    private lateinit var progressLogin: ProgressBar

    /**
     * Stores reference of AuthViewModel class that authenticates or register user
     */
    private lateinit var viewModel: AuthViewModel

    /**
     * Stores reference of ActivityLoginBinding which binds data to UI
     */
    private lateinit var binding: ActivityLoginBinding

    /**
     * Stores reference of sharedPreferences to be passed in UserRepository
     */
    private lateinit var sharedPreferences: SharedPreferences

    /**
     * Stores reference of AuthViewModelFactory
     */
    private lateinit var factory: AuthViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Initialising data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        progressLogin = binding.activityLoginProgress

        //Initialising sharedPreferences
        sharedPreferences = getSharedPreferences("AUTH_PREFS", Context.MODE_PRIVATE)
        //Initialising AuthViewModelFactory
        factory = AuthViewModelFactory(sharedPreferences)

        //Initialising viewModel using ViewModelProvider and passing AuthViewModelFactory instance in the parameter
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
        //Observing change in login status
        viewModel.getLoginStatusLiveData().observe(this) {
            if (it != null) {
                if (it) {
                    //Starts UserDetailsActivity class
                    val intent = Intent(this@LoginActivity, UserDetailsActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    //Hides progress bar
                    progressLogin.hide()
                    //Shows toast of login error
                    toast("Login error: Something went wrong")
                }
            }
        }

        //Adding on click listener on btnLogin that logins user
        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        //Adding on click listener on register text that starts RegisterActivity
        binding.textRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    /**
     * Validates the input information given by the user and return true if all information is valid and invalid otherwise
     */
    private fun validateEmailPassword(email: String, password: String): Boolean {
        // Check for a valid email address.
        val isEmailValid: Boolean = if (email.isEmpty()) {
            false
        } else Patterns.EMAIL_ADDRESS.matcher(email).matches()

        // Check for a valid password.
        val isPasswordValid: Boolean = if (password.isEmpty()) {
            false
        } else password.length >= 6

        return isEmailValid and isPasswordValid
    }

    /**
     * Logins user using viewModel
     */
    private fun loginUser() {
        hideKeyboard()
        val email = binding.textLoginEmail.text.toString()
        val password = binding.textLoginPassword.text.toString()

        if (validateEmailPassword(email, password)) {
            progressLogin.show()
            viewModel.userLogin(email, password)

        } else {
            progressLogin.hide()
        }
    }

    /**
     * Hides keyboard
     */
    private fun hideKeyboard() {
        try {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}