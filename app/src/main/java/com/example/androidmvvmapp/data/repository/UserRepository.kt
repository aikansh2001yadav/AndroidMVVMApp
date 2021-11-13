package com.example.androidmvvmapp.data.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.androidmvvmapp.data.model.User
import com.example.androidmvvmapp.data.preference.PreferenceHelper
import com.example.androidmvvmapp.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Repository class that authenticates user and registers user
 */
class UserRepository(private val sharedPreferences: SharedPreferences) {

    /**
     * Stores an instance of firestore
     */
    private val db = Firebase.firestore

    /**
     * Stores reference of firebase auth
     */
    private var auth: FirebaseAuth? = null

    /**
     * Stores an instance of loginStatusLiveData that contains whether the user is logged in or not
     */
    private val loginStatusLiveData = MutableLiveData<Boolean>()

    init {
        //Initialising auth and updates loginStatusLiveData value to be true if the user is already logged in
        auth = FirebaseAuth.getInstance()
        if (auth!!.currentUser != null) {
            loginStatusLiveData.postValue(true)
        }
    }

    /**
     * Sign in the user and updates loginStatusLiveData
     */
    fun login(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            auth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth!!.currentUser
                    loginStatusLiveData.postValue(true)
                    getUserDetails(user!!.uid)
                } else {
                    loginStatusLiveData.postValue(false)
                    Log.e("LOGIN_STATUS", "Login error: Something went wrong")
                }
            }
        }
    }

    /**
     * Save user details of the current user via shared preferences
     */
    private fun getUserDetails(userID: String) {
        val docRef = db.collection(Constants.USERS).document(userID)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document.data != null) {
                    val user: User = document.toObject(User::class.java)!!
                    PreferenceHelper(sharedPreferences).saveUserProfileDetails(user)
                    Log.i("PROFILE_DETAILS_GET", "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.e("PROFILE_DETAILS_GET", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("PROFILE_DETAILS_GET", "get failed with ", exception)
            }
    }

    /**
     * Registers user and then upload user details on firebase storage
     */
    fun register(name: String, lName: String, email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            auth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth!!.currentUser
                    loginStatusLiveData.postValue(true)
                    uploadUserDetails(User(user!!.uid, name, lName, email))
                } else {
                    loginStatusLiveData.postValue(false)
                    Log.e("REGISTER_STATUS", "Register error: Something went wrong")
                }
            }
        }
    }

    /**
     * Saves user details via shared preferences
     */
    private fun uploadUserDetails(user: User) {

        db.collection(Constants.USERS)
            .document(user.id!!)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                Log.i("PROFILE_DETAILS_UPLOAD", "DocumentSnapshot successfully written!")
                //Saving user details to sharedPreferences
                PreferenceHelper(sharedPreferences).saveUserProfileDetails(user)
            }

    }


    /**
     * Returns reference of loginStatusLiveData
     */
    fun getLoginStatusLiveData(): MutableLiveData<Boolean> {
        return loginStatusLiveData
    }
}