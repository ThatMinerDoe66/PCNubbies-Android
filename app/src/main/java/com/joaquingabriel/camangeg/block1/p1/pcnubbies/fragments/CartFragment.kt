package com.joaquingabriel.camangeg.block1.p1.pcnubbies.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.R
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.adapters.cartfragAdapter
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.api.NubbiesClient
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.CartProduct
import kotlinx.coroutines.launch

class CartFragment : Fragment(R.layout.cartsection),
    cartfragAdapter.OnProductRemoveListener,
    cartfragAdapter.OnQuantityChangeListener {


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

        // Check if there is a saved instance state and restore the cart's state from it
        val restoredCartListJson = savedInstanceState?.getString("cart_list")
        val gson = Gson()
        val cartListType = object : TypeToken<List<CartProduct>>() {}.type
        val restoredCartList: List<CartProduct> = gson.fromJson(restoredCartListJson, cartListType) ?: emptyList()

        // Initialize the adapter with an empty list
        cartAdapter = cartfragAdapter(mutableListOf(), this, this)
        recyclerview.adapter = cartAdapter


        // Fetch cart items from your API
        // If the restored cart list is empty, fetch cart items from your API
        if (restoredCartList.isEmpty()) {
            fetchCartItems()
        }
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

                // Only update the adapter if there's no restored state
                if (cartAdapter.cart_list.isEmpty()) {
                    cartAdapter.updateProducts(updatedCartProducts)
                }
            } else {
                Log.e("CartFragment", "Error fetching cart items: ${response.errorBody()?.string()}")
            }
        }
    }



    override fun onProductRemove(productId: Int) {
        // Find the index of the product to be removed
        val productIndex = cartAdapter.cart_list.indexOfFirst { it.id == productId }
        if (productIndex != -1) {
            // Remove the item locally from the cart list
            cartAdapter.removeItem(productIndex)

            // Launch a coroutine to update the server asynchronously
            lifecycleScope.launch {
                try {
                    // Update the server to remove the item
                    val response = NubbiesClient.instance.removeFromCart(productId)
                    if (!response.isSuccessful) {
                        // If the server update fails, show an error message
                        showError("Failed to remove item from cart. Please try again.")
                    }
                } catch (e: Exception) {
                    // Handle network or other exceptions
                    showError("An error occurred while removing item from cart.")
                }
            }
        }
    }


    private fun showLoadingIndicator() {
        // Show loading indicator (e.g., progress bar or spinner)
    }

    private fun hideLoadingIndicator() {
        // Hide loading indicator
    }

    private fun showError(message: String) {
        // Show error message to the user (e.g., toast or dialog)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val gson = Gson()
        val cartListJson = gson.toJson(cartAdapter.cart_list)
        outState.putString("cart_list", cartListJson)
    }

    override fun onQuantityIncrease(productId: Int) {
        lifecycleScope.launch {
            val productIndex = cartAdapter.cart_list.indexOfFirst { it.id == productId }
            if (productIndex != -1) {
                val product = cartAdapter.cart_list[productIndex]
                val newQuantity = product.cartQuantity + 1
                Log.d("CartFragment", "Increasing quantity for product $productId from ${product.cartQuantity} to $newQuantity")

                // Update the local quantity first
                cartAdapter.updateQuantity(productIndex, newQuantity)

                // Update the quantity on the server
                updateQuantityOnServer(productId, newQuantity)
            }
        }
    }

    override fun onQuantityDecrease(productId: Int) {
        lifecycleScope.launch {
            val productIndex = cartAdapter.cart_list.indexOfFirst { it.id == productId }
            if (productIndex != -1) {
                val product = cartAdapter.cart_list[productIndex]
                val newQuantity = if (product.cartQuantity > 1) product.cartQuantity - 1 else product.cartQuantity

                // Update the local quantity first
                cartAdapter.updateQuantity(productIndex, newQuantity)

                // Update the quantity on the server
                updateQuantityOnServer(productId, newQuantity)
            }
        }
    }

    private suspend fun updateQuantityOnServer(productId: Int, quantity: Int) {
        try {
            val response = NubbiesClient.instance.updateCartQuantity(productId, quantity)
            if (!response.isSuccessful) {
                // Handle error
            }
        } catch (e: Exception) {
            // Handle exception
        }
    }


}