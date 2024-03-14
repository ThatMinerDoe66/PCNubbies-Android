package com.joaquingabriel.camangeg.block1.p1.pcnubbies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.api.AuthPrefs
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.api.NubbiesClient
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.DefaultResponse
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.StringReader

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val inputEmail = findViewById<EditText>(R.id.input_email)
        val inputPassword = findViewById<EditText>(R.id.input_password)

        val loginButton = findViewById<Button>(R.id.login_button)
        val registerButton = findViewById<Button>(R.id.toregister_button)

        // Initialize the NubbiesClient here
        NubbiesClient.setSharedPreferences(this)

        // To Register
        registerButton.setOnClickListener {
            val intent = Intent(this@Login, Register::class.java)
            startActivity(intent)
        }

        // Login Function
        loginButton.setOnClickListener {
            val email = inputEmail.text.toString().trim()
            val password = inputPassword.text.toString().trim()

            // Make the login API call
            NubbiesClient.instance.login(email, password)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            val loginResponse = response.body()!!
                            AuthPrefs.storeApiToken(this@Login, loginResponse.accessToken) // Store the API token
                            Log.d("LoginActivity", "Stored API Token: ${loginResponse.accessToken}")

                            // Access the user's profile information
                            val userProfile = loginResponse.user
                            Toast.makeText(applicationContext, "Logged in as ${userProfile.name}", Toast.LENGTH_LONG).show()

                            val intent = Intent(this@Login, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            val errorMessage: String = try {
                                response.errorBody()?.string() ?: "Failed to get a valid response. Response Code: ${response.code()}"
                            } catch (e: Exception) {
                                "Failed to get a valid response, Response Code: ${response.code()}"
                            }
                            Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_LONG).show()
                            Log.e("API_RESPONSE", errorMessage)
                        }
                    }
                })
        }

    }
}
