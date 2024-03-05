package com.joaquingabriel.camangeg.block1.p1.pcnubbies.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.R
import com.google.android.material.textview.MaterialTextView
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.CartActivity

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Now you can call findViewById on the inflated view
        // Assuming cartButton is a MaterialTextView, not a Button
        val cartButton = view.findViewById<MaterialTextView>(R.id.cartButton)

        // You can set up your button here, for example:
        cartButton.setOnClickListener {
            activity?.let {
                val intent = Intent(it, CartActivity::class.java)
                it.startActivity(intent)
            }
        }

        // Return the inflated view
        return view
    }
}
