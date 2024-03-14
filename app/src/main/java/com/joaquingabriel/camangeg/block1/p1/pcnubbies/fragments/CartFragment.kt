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

class CartFragment : Fragment(R.layout.cartsection), cartfragAdapter.OnProductRemoveListener {

    private lateinit var recyclerview: RecyclerView
    private lateinit var cartAdapter: cartfragAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fetch product images before fetching cart items
        lifecycleScope.launch {
            NubbiesClient.fetchProductResponse()
        }

        recyclerview = view.findViewById(R.id.cartItems)
        recyclerview.layoutManager = LinearLayoutManager(context)

        // Initialize the adapter with an empty list
        cartAdapter = cartfragAdapter(mutableListOf(), this)
        recyclerview.adapter = cartAdapter

        // Fetch cart items from your API
        fetchCartItems()
    }

    private fun fetchCartItems() {
        lifecycleScope.launch {
            val response = NubbiesClient.instance.getCartItems()
            if (response.isSuccessful) {
                val cartResponse = response.body()
                Log.d("NubbiesClient", "CartResponse: $cartResponse")
                val cartItems = cartResponse?.data?.items ?: emptyList()
                val cartProducts = cartResponse?.data?.products ?: emptyList()

                // Assuming the order of items in the list matches the order of products
                val updatedCartProducts = cartProducts.mapIndexed { index, cartProduct ->
                    val cartItemQuantity = cartItems.getOrNull(index)?.quantity ?: 0
                    cartProduct.copy(cartQuantity = cartItemQuantity)
                }

                cartAdapter.updateProducts(updatedCartProducts)
            } else {
                Log.e("CartFragment", "Error fetching cart items: ${response.errorBody()?.string()}")
            }
        }
    }

    override fun onProductRemove(productId: Int) {
        lifecycleScope.launch {
            val response = NubbiesClient.instance.removeFromCart(productId)
            if (response.isSuccessful) {
                // Handle successful removal, e.g., update the UI
                fetchCartItems() // Refresh the cart items
            } else {
                // Handle error
            }
        }
    }
}



