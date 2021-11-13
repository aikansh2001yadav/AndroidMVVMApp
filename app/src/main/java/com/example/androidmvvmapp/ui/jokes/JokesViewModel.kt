package com.example.androidmvvmapp.ui.jokes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidmvvmapp.data.db.JokesDao
import com.example.androidmvvmapp.data.model.Joke
import com.example.androidmvvmapp.data.repository.JokesRepository
import com.example.androidmvvmapp.network.NetworkInterceptor
import com.example.androidmvvmapp.utils.Coroutines
import com.example.androidmvvmapp.utils.UtilExceptions

class JokesViewModel(networkConnectionInterceptor: NetworkInterceptor, jokesDao: JokesDao) :
    ViewModel() {

    /**
     * Declares and initialises jokesRepository
     */
    private val jokesRepository = JokesRepository(networkConnectionInterceptor, jokesDao)

    /**
     *Declares and  Initialises messageLiveData
     */
    private val messageLiveData = MutableLiveData<String>()

    /**
     * Declares and initialises jokesListLiveData mutable live data that contains jokes list
     */
    private val jokesListLiveData = MutableLiveData<ArrayList<Joke>>()

    /**
     * Updates jokes list in jokesListLiveData
     */
    fun getCategoryJokes(category: String) {
        Coroutines.io {
            try {
                val jokesResponse = jokesRepository.getCategoryJokes(category)
                if (!jokesResponse.error) {
                    val jokesList = jokesResponse.jokes
                    if (!jokesList.isNullOrEmpty()) {
                        jokesListLiveData.postValue(jokesResponse.jokes!!)
                    } else {
                        messageLiveData.postValue("Sorry, jokes are not available. Please turn on mobile data or wifi")
                    }
                }
            } catch (e: UtilExceptions.ApiException) {
                messageLiveData.postValue(e.message)
            } catch (e: UtilExceptions.NoConnectivityException) {
                messageLiveData.postValue(e.message)
            } catch (e: UtilExceptions.NoInternetException) {
                messageLiveData.postValue(e.message)
            }
        }
    }

    /**
     * Returns reference of jokesListLiveData
     */
    fun getJokesListLiveData(): MutableLiveData<ArrayList<Joke>> {
        return jokesListLiveData
    }

    /**
     * Returns reference of messageLiveData
     */
    fun getMessageLiveData(): MutableLiveData<String> {
        return messageLiveData
    }
}