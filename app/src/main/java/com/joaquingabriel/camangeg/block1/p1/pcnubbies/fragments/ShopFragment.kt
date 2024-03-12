package com.joaquingabriel.camangeg.block1.p1.pcnubbies.fragments

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

class ShopFragment : Fragment(R.layout.fragment_shop) {

    private lateinit var adapter: cpufragAdapter
    private lateinit var recyclerView: RecyclerView
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        


        recyclerView = view.findViewById(R.id.productlist)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        //initialize adapter with empty list
        adapter = cpufragAdapter(mutableListOf())
        recyclerView.adapter = adapter

        //fetch the product list from API
        fetchProductList()
        
    }
    
    private fun fetchProductList() {
        lifecycleScope.launch {
            val productList = NubbiesClient.fetchProducts(title = null, price = null)
            if (productList != null) {
                adapter.updateProducts(productList)
            } else {
                Toast.makeText(requireContext(), "Failed to load products", Toast.LENGTH_SHORT).show()
            }
        }
    }
}