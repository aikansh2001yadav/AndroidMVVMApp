package com.example.androidmvvmapp.network

import com.example.androidmvvmapp.data.model.Joke
import com.example.androidmvvmapp.network.responses.JokesResponse
import com.example.androidmvvmapp.utils.UtilExceptions
import retrofit2.Response

abstract class SafeApiRequest {
    /**
     * Calls request via retrofit and then return jokes response
     */
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        //Invokes call
        val response = call.invoke()
        //if response is successful, then return response body, else throw ApiException
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            var error = response.message()
            error += response.code()
            throw UtilExceptions.ApiException(error)
        }
    }

    /**
     * Calls request from local database and return jokes response object containing jokes from jokes database
     */
    suspend fun <T : Any> localRequest(call: suspend () -> T): JokesResponse {
        val jokesList = call.invoke()
        return JokesResponse(false, 10, jokesList as ArrayList<Joke>)
    }
}