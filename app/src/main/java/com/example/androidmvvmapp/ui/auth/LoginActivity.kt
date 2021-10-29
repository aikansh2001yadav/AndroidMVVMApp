package com.example.androidmvvmapp.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.androidmvvmapp.MainActivity
import com.example.androidmvvmapp.R
import com.example.androidmvvmapp.databinding.ActivityLoginBinding
import com.example.androidmvvmapp.utils.toast
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.textRegister.setOnClickListener {
//            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun loginUser() {
        val email = binding.textLoginEmail.text.toString()
        val password = binding.textLoginPassword.text.toString()
        lifecycleScope.launch {
            val authResult =viewModel.userLogin(email, password)
            if(authResult){
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }else{
                toast("Authentication failed")
            }
        }
    }
}