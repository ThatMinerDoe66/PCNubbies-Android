package com.joaquingabriel.camangeg.block1.p1.pcnubbies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.fragments.HomeFragment
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.fragments.ShopFragment
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<CurvedBottomNavigation>(R.id.bottomNavigation)
        bottomNavigation.add(
            CurvedBottomNavigation.Model(1, "Home", R.drawable.baseline_home_button)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(2, "Shop", R.drawable.baseline_home_button)
        )

        bottomNavigation.setOnClickMenuListener {
            when(it.id){
                1 ->{
                    replaceFragment(ShopFragment())
                }
                2 ->{
                    replaceFragment((HomeFragment()))
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

}