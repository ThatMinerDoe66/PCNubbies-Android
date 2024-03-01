package com.joaquingabriel.camangeg.block1.p1.pcnubbies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.fragments.GuideFragment
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.fragments.HomeFragment
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.fragments.ProfileFragment
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.fragments.ShopFragment
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<CurvedBottomNavigation>(R.id.bottomNavigation)
        addFragment(HomeFragment())
        bottomNavigation.show(1)
        bottomNavigation.add(
            CurvedBottomNavigation.Model(0, "Shop", R.drawable.baseline_home_button)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(1, "Home", R.drawable.baseline_home_button)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(2, "Guide", R.drawable.baseline_guide_button)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(3, "Cart", R.drawable.baseline_profile_button)
        )

        bottomNavigation.setOnClickMenuListener {
            when(it.id){
                0 ->{
                    replaceFragment(ShopFragment())
                }
                1 ->{
                    replaceFragment(HomeFragment())
                }
                2 ->{
                    replaceFragment(GuideFragment())
                }
                3 ->{
                    replaceFragment(ProfileFragment())
                }
            }
        }

    }
    private fun replaceFragment(fragment:Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun addFragment(fragment:Fragment){
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, fragment)
            .commit()
    }


}