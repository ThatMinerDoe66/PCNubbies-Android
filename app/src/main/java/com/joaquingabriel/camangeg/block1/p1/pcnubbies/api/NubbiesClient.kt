package com.joaquingabriel.camangeg.block1.p1.pcnubbies.api

import android.content.SharedPreferences
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





    private fun createInstance(): NubbiesAPI{
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor{chain ->
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
            .client(okHttpClient)
            .build()

        return retrofit.create(NubbiesAPI::class.java)
    }
    private const val BASE_URL = "https://pcnubbies2.pcnubbies.tech/api/"
}