package com.example.androidmvvmapp.ui.jokes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidmvvmapp.data.db.JokesDao
import com.example.androidmvvmapp.network.NetworkInterceptor

class JokesViewModelFactory(
    /**
     * Stores reference of networkConnectionInterceptor that intercepts network
     */
    private val networkConnectionInterceptor: NetworkInterceptor,
    /**
     * Stores reference of jokesDao
     */
    private val jokesDao: JokesDao
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return JokesViewModel(networkConnectionInterceptor, jokesDao) as T
    }
}