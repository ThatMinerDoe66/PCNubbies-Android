package com.joaquingabriel.camangeg.block1.p1.pcnubbies.fragments

import ProductResponse
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.R
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.adapters.cpufragAdapter
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.api.NubbiesClient
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home, ) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val bottomNavigation = view.findViewById<CurvedBottomNavigation>(R.id.bottomNavigation)

//        //from Product Data Class
//        val showproduct: MutableList<Product> = mutableListOf()
//        showproduct.add(Product("Product 1", "Description for Product 1", "@drawable/mobo.png"))
//        showproduct.add(Product("Product 2", "Description for Product 2", "@drawable/mobo.png"))
//        showproduct.add(Product("Product 3", "Description for Product 3", "@drawable/mobo.png"))
//


        //call images from xml
        val shop: ImageView = view.findViewById(R.id.shop)
        val guides: ImageView = view.findViewById(R.id.guides)

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