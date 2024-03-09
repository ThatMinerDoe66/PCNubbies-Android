package com.joaquingabriel.camangeg.block1.p1.pcnubbies.api

import Product
import ProductResponse
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.CartProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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


    suspend fun fetchCartProducts(): List<CartProduct> {
        return withContext(Dispatchers.IO) {
            try {
                val response = NubbiesClient.instance.getCartItems()

                if (response.isSuccessful) {
                    val cartResponse = response.body()
                    cartResponse?.data?.products ?: emptyList()
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





    suspend fun fetchProducts(title: String?, price: Double?): List<Product>? {
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


        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            //.addCallAdapterFactory(CoroutineCallAdapterFactory()) // Add this line
            .client(okHttpClient)
            .build()

        //val instance: NubbiesAPI = retrofit.create(NubbiesAPI::class.java)

        return retrofit.create(NubbiesAPI::class.java)
    }
    private const val BASE_URL = "https://pcnubbies.pcnubbies.tech/api/"
}