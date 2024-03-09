package com.joaquingabriel.camangeg.block1.p1.pcnubbies.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.R
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.adapters.cartfragAdapter
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.api.NubbiesClient
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.CartProduct
import kotlinx.coroutines.launch

class CartFragment : Fragment(R.layout.cartsection) {

    private lateinit var recyclerview: RecyclerView
    private lateinit var cartAdapter: cartfragAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerview = view.findViewById(R.id.cartItems)
        recyclerview.layoutManager = LinearLayoutManager(context)

        // Initialize the adapter with an empty list
        cartAdapter = cartfragAdapter(mutableListOf())
        recyclerview.adapter = cartAdapter

        // Fetch cart items from your API
        fetchCartItems()
    }

    private fun fetchCartItems() {
        // Use lifecycleScope to launch a coroutine
        lifecycleScope.launch {
            // Fetch cart items from your API
            val response = NubbiesClient.instance.getCartItems()
            if (response.isSuccessful) {
                val cartResponse = response.body()
                // Extract the products list from the Data object
                val cartProducts = cartResponse?.data?.products ?: emptyList()
                cartAdapter.updateProducts(cartProducts)
            } else {
                // Handle error, e.g., show a message to the user
                Log.e(
                    "CartFragment",
                    "Error fetching cart items: ${response.errorBody()?.string()}"
                )
            }
        }
    }

}

