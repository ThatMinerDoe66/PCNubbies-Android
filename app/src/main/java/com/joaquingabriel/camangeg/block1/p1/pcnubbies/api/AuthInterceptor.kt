package com.joaquingabriel.camangeg.block1.p1.pcnubbies.api

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val apiToken = AuthPrefs.getApiToken(context)

        if (apiToken == null) {
            Log.e("AuthInterceptor", "API token is null")
            return chain.proceed(originalRequest)
        }

        val requestBuilder = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $apiToken")
        val request = requestBuilder.build()
        Log.d("AuthInterceptor", "Added Authorization header: $request")
        return chain.proceed(request)
    }
}