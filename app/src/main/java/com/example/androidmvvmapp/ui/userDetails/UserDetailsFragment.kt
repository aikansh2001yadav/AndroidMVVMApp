package com.example.androidmvvmapp.ui.userDetails

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.androidmvvmapp.databinding.FragmentUserDetailsBinding
import com.example.androidmvvmapp.ui.auth.LoginActivity
import com.example.androidmvvmapp.utils.Constants
import com.example.androidmvvmapp.utils.hide
import com.example.androidmvvmapp.utils.show

class UserDetailsFragment : Fragment() {

    /**
     * Stores reference of user details progress bar
     */
    private lateinit var progressUserDetails: ProgressBar

    /**
     * Stores reference of sharedPreferences
     */
    private lateinit var sharedPreferences: SharedPreferences

    /**
     * Stores reference of userDetailsViewModelFactory
     */
    private lateinit var factory: UserDetailsViewModelFactory

    /**
     * Stores reference of userDetailsViewModel
     */
    private lateinit var userDetailsViewModel: UserDetailsViewModel

    /**
     * Stores reference of FragmentUserDetailsBinding that binds data to UI
     */
    private var _binding: FragmentUserDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Initialising
        sharedPreferences =
            requireActivity().getSharedPreferences(Constants.USERS_PREFS, Context.MODE_PRIVATE)
        factory = UserDetailsViewModelFactory(sharedPreferences)
        userDetailsViewModel =
            ViewModelProvider(this, factory)[UserDetailsViewModel::class.java]

        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)

        progressUserDetails = binding.progressUserDetails

        val root: View = binding.root
        progressUserDetails.show()
        userDetailsViewModel.getUserDetails()
        //Observing change in user details
        userDetailsViewModel.getUserDetailsLiveData().observe(viewLifecycleOwner) {
            if (it != null) {
                //Sets user details
                binding.textUserName.text = it.name.toString()
                binding.textUserLname.text = it.lastName.toString()
                binding.textUserEmail.text = it.email.toString()
            }
            //Hides user details progress
            progressUserDetails.hide()
        }
        //Observing change in user logout liveData
        userDetailsViewModel.getUserLogoutLiveData().observe(viewLifecycleOwner) {
            if (it != null) {
                progressUserDetails.hide()
                //Starts LoginActivity class
                startActivity(Intent(context, LoginActivity::class.java))
                requireActivity().finish()
            }
            progressUserDetails.hide()
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Setting on click listener on btnLogout that logout current user
        binding.btnLogout.setOnClickListener {
            progressUserDetails.show()
            //Logout user
            userDetailsViewModel.userLogout()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}