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
import androidx.lifecycle.lifecycleScope
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.R
import com.google.android.material.textview.MaterialTextView
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.CartActivity
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.api.AuthPrefs
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.api.NubbiesClient
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.UserProfile
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)


        // Set up the cart button click listener
        val cartButton = view.findViewById<AppCompatImageView>(R.id.cartButton)
        cartButton.setOnClickListener {
            activity?.let {
                val intent = Intent(it, CartActivity::class.java)
                it.startActivity(intent)
            }
        }

        // Fetch user profile and update UI
        lifecycleScope.launch {
            val apiToken = AuthPrefs.getApiToken(requireContext())
            Log.d("ProfileFragment", "Retrieved API Token: $apiToken")
            // Check if apiToken is not null before making the API call
            apiToken?.let { nonNullApiToken ->
                val response = NubbiesClient.instance.fetchUserProfile(nonNullApiToken)
                Log.d("ProfileFragment", "API response: ${response.body()}")

                val userProfile = response.body()
                Log.d("ProfileFragment", "API response: $response")
                Log.d("ProfileFragment", "Parsed UserProfile: $userProfile")

                userProfile?.let {
                    val profileNameTextView = view.findViewById<TextView>(R.id.profile_name)
                    val profileEmailTextView = view.findViewById<TextView>(R.id.profile_email)

                    profileNameTextView.text = it.user.name
                    profileEmailTextView.text = it.user.email
                    Log.d("ProfileFragment", "Setting UI with name: ${it.user.name} and email: ${it.user.email}")
                }
            } ?: run {
                Log.e("ProfileFragment", "API Token is null")
            }
        }

        return view
    }

    private suspend fun fetchUserProfile(apiToken: String?): UserProfile? {
        return apiToken?.let {
            val response = NubbiesClient.instance.fetchUserProfile(it)
            Log.d("ProfileFragment", "API response: ${response.body()}")
            response.body()
        }
    }
}

