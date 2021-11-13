package com.example.androidmvvmapp.data.repository

import com.example.androidmvvmapp.data.db.JokesDao
import com.example.androidmvvmapp.data.model.Joke
import com.example.androidmvvmapp.network.JokesApi
import com.example.androidmvvmapp.network.NetworkInterceptor
import com.example.androidmvvmapp.network.SafeApiRequest
import com.example.androidmvvmapp.network.responses.JokesResponse
import com.example.androidmvvmapp.utils.UtilExceptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository class that fetches jokes of various categories and stores them in sharedPreferences
 */
class JokesRepository(
    /**
     * Stores reference of NetworkInterceptor that intercepts network
     */
    networkConnectionInterceptor: NetworkInterceptor,
    /**
     * Stores reference of jokes dao from jokes database
     */
    private val jokesDao: JokesDao
) :
    SafeApiRequest() {
    private val jokesApi = JokesApi(networkConnectionInterceptor)

    /**
     * Returns jokes response that contains jokes from the category
     */
    suspend fun getCategoryJokes(category: String): JokesResponse {
        return try {
            val response = apiRequest { jokesApi.getCategoryJokes(category, 10) }
            withContext(Dispatchers.Default) {
                //Delete category jokes belonging to a particular category
                jokesDao.deleteCategoryJokes(category)
                if (response.jokes != null) {
                    //Inserts all jokes in the jokes database
                    for (joke in response.jokes) {
                        if (joke.type == "single") {
                            jokesDao.insertJoke(
                                Joke(
                                    joke.category,
                                    joke.type,
                                    joke.flags,
                                    joke.id,
                                    joke.safe,
                                    joke.lang,
                                    joke.joke
                                )
                            )
                        } else {
                            jokesDao.insertJoke(
                                Joke(
                                    joke.category,
                                    joke.type,
                                    joke.setup,
                                    joke.delivery,
                                    joke.flags,
                                    joke.id,
                                    joke.safe,
                                    joke.lang
                                )
                            )
                        }
                    }
                }
            }
            response
        } catch (e: UtilExceptions.NoConnectivityException) {
            //If there is no connection, return jokes response that contains jokes from the jokes database
            localRequest { jokesDao.getCategoryJokes(category) }
        }
    }
}