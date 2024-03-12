package com.joaquingabriel.camangeg.block1.p1.pcnubbies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.R
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.databinding.FragmentPresentationBinding

class PresentationFragment : Fragment() {
    private lateinit var binding:FragmentPresentationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.button.setOnClickListener {
            startGuideFragment()
        }
        return inflater.inflate(R.layout.fragment_presentation, container, false)
        return binding.root
    }
    private fun startGuideFragment(){
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(android.R.id.content, GuideFragment())
            .addToBackStack(null)
            .commit()
    }

}