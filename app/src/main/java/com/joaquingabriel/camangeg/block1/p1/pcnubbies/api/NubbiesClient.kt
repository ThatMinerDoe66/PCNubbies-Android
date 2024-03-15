package com.joaquingabriel.camangeg.block1.p1.pcnubbies.api

import Product
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.GsonBuilder
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.AddToCartRequest
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.CartProduct
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.DefaultResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NubbiesClient {
    private lateinit var sharedPreferences: SharedPreferences
    internal lateinit var instance: NubbiesAPI

    fun setSharedPreferences(context: Context) {
        // Assuming you have a way to get SharedPreferences from the context
        sharedPreferences = context.getSharedPreferences("YourSharedPrefsName", Context.MODE_PRIVATE)
        // Initialize the instance here with the context
        instance = createInstance(context)
    }

    val productImagesMap = mutableMapOf<String, String>()

    suspend fun fetchProductResponse(): Map<String, String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = instance.getProductList(null, null) // Adjust this call based on your actual API endpoint
                if (response.isSuccessful) {
                    val productResponse = response.body()
                    productResponse?.data?.forEach { product ->
                        // Assuming you want the first image URL for each product
                        val imageUrl = product.product_images?.firstOrNull()?.image ?: "No image URL found"
                        productImagesMap[product.id.toString()] = imageUrl
                    }
                } else {
                    Log.e("NubbiesClient", "Error fetching product response: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("NubbiesClient", "Error fetching product response", e)
            }
            productImagesMap
        }
    }


    suspend fun fetchCartProducts(): List<CartProduct> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NubbiesClient.instance.getCartItems()

                if (response.isSuccessful) {
                    val cartResponse = response.body()
                    cartResponse?.data?.let { data ->
                        // Log the image URLs for each product
                        data.products.forEach { cartProduct ->
                            val imageUrl = cartProduct.product_images?.firstOrNull()?.image ?: "No image URL found"
                            Log.d("cartfragAdapter", "Product: ${cartProduct.title}, Image URL: $imageUrl")
                        }

                        // Map the quantity from CartItem to CartProduct
                        data.products.map { cartProduct ->
                            val cartItem = data.items.find { it.productId == cartProduct.id }
                            val updatedCartProduct = cartProduct.copy(cartQuantity = cartItem?.quantity ?: 0)
                            Log.d("NubbiesClient", "Product: ${cartProduct.title}, Cart Quantity: ${updatedCartProduct.cartQuantity}")
                            updatedCartProduct
                        }
                    } ?: emptyList()
                } else {
                    Log.e("NubbiesClient", "Error fetching cart products: ${response.errorBody()?.string()}")
                    emptyList()
                }
            } catch (e: Exception) {
                Log.e("NubbiesClient", "Error fetching cart products", e)
                emptyList()
            }
        }
    }




    suspend fun addToCart(productId: Int, quantity: Int): Response<DefaultResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val request = AddToCartRequest(quantity)
                val response = instance.addToCart(productId, request)
                if (response.isSuccessful) {
                    response
                } else {
                    throw Exception("Error adding product to cart: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("NubbiesClient", "Error adding product to cart", e)
                throw e
            }
        }
    }


    suspend fun fetchProducts(title: String?, price: Double?): List<Product>? { //Fetches all products
        return withContext(Dispatchers.IO) {
            try {
                val response = NubbiesClient.instance.getProductList(title, price)
                if (response.isSuccessful) {
                    val productResponse = response.body()
                    if (productResponse != null) {
                        // Extract the list of Product objects from the ProductResponse object
                        productResponse.data
                    } else {
                        emptyList<Product>()
                    }
                } else {
                    emptyList<Product>()
                }
            } catch (e: Exception) {
                Log.e("NubbiesClient", "Error fetching products", e)
                emptyList<Product>()
            }
        }
    }

    private fun createInstance(context: Context): NubbiesAPI {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context)) // Use the AuthInterceptor here
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)) // Use the modified Gson instance here
            .client(okHttpClient)
            .build()

        return retrofit.create(NubbiesAPI::class.java)
    }
    private const val BASE_URL = "https://pcnubbies.pcnubbies.tech/api/"
}
