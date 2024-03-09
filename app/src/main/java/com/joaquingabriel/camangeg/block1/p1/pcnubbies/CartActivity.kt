package com.joaquingabriel.camangeg.block1.p1.pcnubbies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.api.NubbiesClient
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.fragments.CartFragment
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.fragments.GuideFragment
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.fragments.HomeFragment
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.fragments.ProfileFragment
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.fragments.ShopFragment
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        //add CartFragment here
        addFragment(CartFragment())

        // Initialize NubbiesClient with the context of this Activity
        Log.d("NubbiesClient", "Initializing NubbiesClient")
        NubbiesClient.setSharedPreferences(this)
        Log.d("NubbiesClient", "NubbiesClient initialized")


    }
    private fun replaceFragment(fragment:Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerCart, fragment)
            .commit()
    }

    private fun addFragment(fragment:Fragment){
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainerCart, fragment)
            .commit()
    }


}