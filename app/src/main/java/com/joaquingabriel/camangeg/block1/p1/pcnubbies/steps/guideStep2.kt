package com.joaquingabriel.camangeg.block1.p1.pcnubbies.steps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.R

class guideStep2 : Fragment() {

    private lateinit var recyclerview: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val goBack: ImageView = view.findViewById(R.id.backGuides_button)
        val back: ImageView = view.findViewById(R.id.back_button)
        val next: ImageView = view.findViewById(R.id.next_button)

        goBack.setOnClickListener {
            replaceFragment(GuideFragment())
        }

        back.setOnClickListener {
            replaceFragment(guideStep1())
        }

        next.setOnClickListener {
            replaceFragment(guideStep3())
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.step2_guide, container, false)
    }

    private fun replaceFragment(fragment:Fragment){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun addFragment(fragment:Fragment){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, fragment)
            .commit()
    }
}