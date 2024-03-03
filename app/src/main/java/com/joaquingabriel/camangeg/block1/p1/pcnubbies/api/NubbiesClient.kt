package com.joaquingabriel.camangeg.block1.p1.pcnubbies.api

import ProductResponse
import android.content.SharedPreferences
import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NubbiesClient {
    private lateinit var sharedPreferences: SharedPreferences

    val instance: NubbiesAPI by lazy { createInstance() }

    fun setSharedPreferences(kek:SharedPreferences){
        sharedPreferences = kek
    }

    suspend fun fetchProducts(title: String?, price: Double?): List<ProductResponse>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = NubbiesClient.instance.getProductList(title, price)
                if (response.isSuccessful) {
                    val productResponse = response.body()
                    if (productResponse != null) {
                        // Assuming productResponse.data is the correct property to return
                        // This should return a List<ProductResponse>
                        listOf(productResponse) // Wrap the productResponse in a list
                    } else {
                        emptyList<ProductResponse>()
                    }
                } else {
                    emptyList<ProductResponse>()
                }
            } catch (e: Exception) {
                Log.e("NubbiesClient", "Error fetching products", e)
                emptyList<ProductResponse>()
            }
        }
    }


    private fun createInstance(): NubbiesAPI {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory()) // Add this line
            .client(okHttpClient)
            .build()

        return retrofit.create(NubbiesAPI::class.java)
    }
    private const val BASE_URL = "https://pcnubbies2.pcnubbies.tech/api/"
}