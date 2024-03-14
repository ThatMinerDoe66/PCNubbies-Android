package com.joaquingabriel.camangeg.block1.p1.pcnubbies.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.R
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.CartActivity
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.adapters.profileAdapter
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.LoginResponse
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.UserProfile
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.UserViewModel

class ProfileFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize the ViewModel
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        // Observe the user data from the ViewModel
        userViewModel.userData.observe(viewLifecycleOwner, { loginResponseUserProfile ->
            Log.d(
                "ProfileFragment",
                "User name: ${loginResponseUserProfile.name}, Email: ${loginResponseUserProfile.email}"
            )
            // Update the UI with the user's data
            view.findViewById<TextView>(R.id.profile_name).text = loginResponseUserProfile.name
            view.findViewById<TextView>(R.id.profile_email).text = loginResponseUserProfile.email
        })

        val cartButton = view.findViewById<AppCompatImageView>(R.id.cartButton)
        cartButton.setOnClickListener {
            activity?.let {
                val intent = Intent(it, CartActivity::class.java)
                it.startActivity(intent)
            }
        }

        return view
    }
}