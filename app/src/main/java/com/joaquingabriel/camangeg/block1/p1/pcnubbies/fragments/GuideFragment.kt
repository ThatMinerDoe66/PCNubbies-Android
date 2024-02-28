package com.joaquingabriel.camangeg.block1.p1.pcnubbies.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.R
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.steps.guideStep1
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.steps.guideStep2
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.steps.guideStep3
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.steps.guideStep4
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.steps.guideStep5
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.steps.guideStep6
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.steps.guideStep7
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class GuideFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val bottomNavigation = view.findViewById<CurvedBottomNavigation>(R.id.bottomNavigation)

        //call images from xml
        val step1: ImageView = view.findViewById(R.id.guide_step1)
        val step2: ImageView = view.findViewById(R.id.guide_step2)
        val step3: ImageView = view.findViewById(R.id.guide_step3)
        val step4: ImageView = view.findViewById(R.id.guide_step4)
        val step5: ImageView = view.findViewById(R.id.guide_step5)
        val step6: ImageView = view.findViewById(R.id.guide_step6)
        val step7: ImageView = view.findViewById(R.id.guide_step7)




        //onClickListener
        step1.setOnClickListener{
            replaceFragment(guideStep1())
        }
        step2.setOnClickListener{
            replaceFragment(guideStep2())
        }
        step3.setOnClickListener{
            replaceFragment(guideStep3())
        }
        step4.setOnClickListener{
            replaceFragment(guideStep4())
        }
        step5.setOnClickListener{
            replaceFragment(guideStep5())
        }
        step6.setOnClickListener{
            replaceFragment(guideStep6())
        }
        step7.setOnClickListener{
            replaceFragment(guideStep7())
        }






    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guide, container, false)
    }

    private fun replaceFragment(fragment: Fragment) {
        try {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
        } catch (e: Exception) {
            // Log the exception
            Log.e("GuideFragment", "Error replacing fragment", e)
        }
    }

    private fun addFragment(fragment: Fragment) {
        try {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .addToBackStack(null) // Consider adding this for better navigation
                .commit()
        } catch (e: Exception) {
            // Log the exception
            Log.e("GuideFragment", "Error adding fragment", e)
        }
    }
}