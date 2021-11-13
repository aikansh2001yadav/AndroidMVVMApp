package com.example.androidmvvmapp.network

import com.example.androidmvvmapp.network.responses.JokesResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Custom Retrofit api and that returns jokes belonging to a particular category
 */
interface JokesApi {
    /**
     * Returns jokes belonging to a particular category
     */
    @GET("{category}" +"?")
    suspend fun getCategoryJokes( @Path("category")category : String, @Query("amount") amount: Int): Response<JokesResponse>


    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkInterceptor): JokesApi {
            val okHttpClient =
                OkHttpClient.Builder().addInterceptor(networkConnectionInterceptor).build()
            return Retrofit.Builder()
                .baseUrl("https://v2.jokeapi.dev/joke/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(JokesApi::class.java)
        }
    }
}