package com.joaquingabriel.camangeg.block1.p1.pcnubbies.api

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object AuthPrefs {
    private const val PREFS_NAME = "AuthPreferences"
    private const val API_TOKEN = "access_token"


    fun storeApiToken(context: Context, apiToken: String?) {
        if (apiToken == null) {
            Log.e("AuthPrefs", "API token is null")
            return
        }
        // Existing implementation to store the apiToken
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(API_TOKEN, apiToken) // Ensure API_TOKEN is correctly defined in your AuthPrefs
        editor.apply()

        // Log the stored API token
        Log.d("AuthPrefs", "Storing API token: $apiToken")
    }


    fun getApiToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val token = sharedPreferences.getString(API_TOKEN, null)

        // Log the retrieved API token
        Log.d("AuthPrefs", "Retrieved API token: $token")

        return token
    }

}
