package com.joaquingabriel.camangeg.block1.p1.pcnubbies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.R
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.steps.guideStep1
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
            replaceFragment(GuideFragment())
        }
        step3.setOnClickListener{
            replaceFragment(GuideFragment())
        }
        step4.setOnClickListener{
            replaceFragment(GuideFragment())
        }
        step5.setOnClickListener{
            replaceFragment(GuideFragment())
        }
        step6.setOnClickListener{
            replaceFragment(GuideFragment())
        }
        step7.setOnClickListener{
            replaceFragment(GuideFragment())
        }






    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
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