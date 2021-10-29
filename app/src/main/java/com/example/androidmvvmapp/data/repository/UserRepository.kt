package com.example.androidmvvmapp.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


class UserRepository {

    private var authResult = false
    suspend fun login(email: String, password: String): Boolean {
        val auth = FirebaseAuth.getInstance()
//        val job = Job()
//        CoroutineScope(Dispatchers.IO + job).launch {
//            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{task ->
//                authResult = task.isSuccessful
//            }
//        }.join()
        val signIn = CoroutineScope(Dispatchers.IO).async {
            auth.signInWithEmailAndPassword(email, password)
        }.await()
//        auth.signInWithEmailAndPassword(email, password).await()
//        Log.e("LOGIN_STATUS", auth.currentUser.toString())
        val result = auth.currentUser!=null
        Log.i("LOGIN_STATUS", result.toString())
        return true
    }

    suspend fun register(email: String, password: String): Boolean {
        val auth = FirebaseAuth.getInstance()
        val authStatus = CoroutineScope(Dispatchers.IO).async {
            auth.createUserWithEmailAndPassword(email, password)
        }
        return authStatus.await().isSuccessful
    }
}