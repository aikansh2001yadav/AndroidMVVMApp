package com.example.androidmvvmapp.ui.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.androidmvvmapp.R
import com.example.androidmvvmapp.ui.UserDetailsActivity
import com.example.androidmvvmapp.databinding.ActivityRegisterBinding
import com.example.androidmvvmapp.utils.hide
import com.example.androidmvvmapp.utils.show
import com.example.androidmvvmapp.utils.toast

class RegisterActivity : AppCompatActivity() {
    /**
     * Stores reference of register progress bar
     */
    private lateinit var progressRegister: ProgressBar

    /**
     * Stores reference of AuthViewModel class that authenticates or register user
     */
    private lateinit var viewModel: AuthViewModel

    /**
     * Stores reference of ActivityRegisterBinding that binds data to UI
     */
    private lateinit var binding: ActivityRegisterBinding

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

        //Initialising shared preferences
        sharedPreferences = getSharedPreferences("AUTH_PREFS", Context.MODE_PRIVATE)
        //Initialising AuthViewModelFactory
        factory = AuthViewModelFactory(sharedPreferences)

        //Initialising data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        //Initialising register progress bar
        progressRegister = binding.progressRegisterActivity
        //Initialising viewModel using ViewModelProvider
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        //Observing change in loginStatus in viewModel
        viewModel.getLoginStatusLiveData().observe(this) {
            if (it != null) {
                if (it) {
                    //Starts UserDetailsActivity
                    val intent = Intent(this@RegisterActivity, UserDetailsActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    //Hides register progress bar
                    progressRegister.hide()
                    //Shows toast message of Register error
                    toast("Registration error : Something went wrong")
                }
            }
        }

        //Sets on click listener on btnRegister that registers user
        binding.btnRegister.setOnClickListener {
            registerUser()
        }
        //Sets on click listener on textLoginBack that finishes current activity
        binding.textLoginBack.setOnClickListener {
            finish()
        }
    }

    /**
     * Validates input information given by the user and give suggestions
     */
    private fun validateDetails(
        name: String,
        lName: String,
        email: String,
        password: String
    ): Boolean {
        //Stores confirm password entered by the user
        val confirmPassword =
            binding.textConfirmPassword.text.toString()

        //Giving suggestions to the user if something is not valid and return false otherwise return true
        when {
            name.isEmpty() -> {
                Toast.makeText(this@RegisterActivity, "Please enter name", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            lName.isEmpty() -> {
                Toast.makeText(this@RegisterActivity, "Please enter last name", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            email.isEmpty() or !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                Toast.makeText(
                    this@RegisterActivity,
                    "Please enter email correctly",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            password.isEmpty() or (password.length < 6) -> {
                Toast.makeText(
                    this@RegisterActivity,
                    "Please enter password correctly",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            confirmPassword != password -> {
                Toast.makeText(
                    this@RegisterActivity,
                    "Entered password is not same as you typed earlier",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            !binding.checkboxTerms.isChecked -> {
                Toast.makeText(
                    this@RegisterActivity,
                    "Please agree to all terms and conditions",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            else -> {
                return true
            }
        }
    }

    /**
     * Registers user and updates loginStatusLiveData
     */
    private fun registerUser() {
        hideKeyboard()
        val name = binding.textRegisterName.text.toString()
        val lName = binding.textRegisterLname.text.toString()
        val email = binding.textRegisterEmail.text.toString()
        val password = binding.textRegisterPass.text.toString()

        if (validateDetails(name, lName, email, password)) {
            //Shows register progress bar
            progressRegister.show()
            //Registers user
            viewModel.userRegister(name, lName, email, password)

        } else {
            //Hides register progress bar
            progressRegister.hide()
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