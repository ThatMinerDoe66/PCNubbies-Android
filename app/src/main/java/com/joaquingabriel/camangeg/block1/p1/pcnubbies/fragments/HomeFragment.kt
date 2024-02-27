package com.joaquingabriel.camangeg.block1.p1.pcnubbies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.R
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigation = view.findViewById<CurvedBottomNavigation>(R.id.bottomNavigation)

        //call images from xml
        val shop : ImageView = view.findViewById(R.id.shop)
        val guides : ImageView = view.findViewById(R.id.guides)


        //onClickListener
        guides.setOnClickListener{
            //bottomNavigation.show(2)  <<Wtf, I can't update the navbar on this one!
            replaceFragment(GuideFragment())
        }

        shop.setOnClickListener{
            //bottomNavigation.show(0)   <<AAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHH
            replaceFragment(ShopFragment())
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