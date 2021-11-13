package com.example.androidmvvmapp.network.responses


import com.example.androidmvvmapp.data.model.Joke
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Data class that contains jokes list and some other details of jokes response
 */
data class JokesResponse(
    /**
     * Stores error of the joke
     */
    @SerializedName("error")
    @Expose
    val error: Boolean,
    /**
     * Stores amount of jokes
     */
    @SerializedName("amount")
    @Expose
    val amount: Int,
    /**
     * Stores list of jokes in jokes response
     */
    @SerializedName("jokes")
    @Expose
    val jokes: ArrayList<Joke>? = null
)