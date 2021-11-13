package com.example.androidmvvmapp.ui.jokes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmvvmapp.controller.CategoryAdapter
import com.example.androidmvvmapp.controller.JokesAdapter
import com.example.androidmvvmapp.data.db.JokesDao
import com.example.androidmvvmapp.data.db.JokesDatabase
import com.example.androidmvvmapp.databinding.FragmentJokesBinding
import com.example.androidmvvmapp.network.NetworkInterceptor
import com.example.androidmvvmapp.utils.hide
import com.example.androidmvvmapp.utils.show
import com.example.androidmvvmapp.utils.toast

class JokesFragment : Fragment() {

    /**
     * Stores reference of jokes dao that returns list of jokes
     */
    private lateinit var jokesDao: JokesDao

    /**
     * Stores reference of FragmentJokesBinding that binds data to UI
     */
    private var _binding: FragmentJokesBinding? = null

    /**
     * Stores reference of jokes progress bar
     */
    private lateinit var jokesProgress: ProgressBar

    /**
     * Stores reference of JokesViewModel class
     */
    private lateinit var jokesViewModel: JokesViewModel

    /**
     * Stores reference of categoryRecyclerView that shows categories of jokes
     */
    private lateinit var categoryRecyclerView: RecyclerView

    /**
     * Stores reference of jokesRecyclerView that shows list of jokes
     */
    private lateinit var jokesRecyclerView: RecyclerView

    /**
     * Stores reference of networkConnectionInterceptor that intercepts internet
     */
    private lateinit var networkConnectionInterceptor: NetworkInterceptor

    /**
     * Stores reference of jokesViewModelFactory
     */
    private lateinit var jokesViewModelFactory: JokesViewModelFactory

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Initialises jokesDao
        jokesDao =
            JokesDatabase(requireContext()).jokesDao()
        //Initialises networkConnectionInterceptor
        networkConnectionInterceptor = NetworkInterceptor(requireContext())
        //Initialises jokesViewModelFactory
        jokesViewModelFactory = JokesViewModelFactory(networkConnectionInterceptor, jokesDao)
        //Initialises jokesViewModel using ViewModelProvider
        jokesViewModel =
            ViewModelProvider(this, jokesViewModelFactory)[JokesViewModel::class.java]

        //Initialises FragmentJokesBinding
        _binding = FragmentJokesBinding.inflate(inflater, container, false)

        //Initialises jokes progress bar
        jokesProgress = binding.progressJokes
        val root: View = binding.root

        //Updates UI
        updateUI("Programming")
        //Observing change in jokesLiveData that initialises JokesAdapter
        jokesViewModel.getJokesListLiveData().observe(viewLifecycleOwner) {
            if (it != null) {
                jokesRecyclerView.adapter = JokesAdapter(requireContext(), it)
            }
            jokesProgress.hide()
        }
        //Observing change in messageLiveData that shows toasts of given string it
        jokesViewModel.getMessageLiveData().observe(viewLifecycleOwner) {
            if (it != null) {
                requireContext().toast(it)
                jokesProgress.hide()
            }
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /**
         * Stores reference of categoryRecyclerView that shows category of jokes
         */
        categoryRecyclerView = binding.recyclerviewCategory
        /**
         * Stores reference of jokesRecyclerView that shows jokes list
         */
        jokesRecyclerView = binding.recyclerviewJokes

        //Sets adapter in categoryRecyclerView
        categoryRecyclerView.adapter = CategoryAdapter(
            this,
            arrayListOf("Programming", "Dark", "Pun", "Spooky", "Christmas", "Misc")
        )
        //Sets adapter in jokesRecyclerView
        jokesRecyclerView.adapter = JokesAdapter(requireContext(), arrayListOf())

        categoryRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        jokesRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Updates UI that refreshes jokes of a particular category
     */
    fun updateUI(category: String) {
        jokesProgress.show()
        jokesViewModel.getCategoryJokes(category)
    }
}