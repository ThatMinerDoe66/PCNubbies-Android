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
    private lateinit var radioButton: RadioButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val inputEmail = findViewById<EditText>(R.id.input_email)
        val inputPassword = findViewById<EditText>(R.id.input_password)

        val loginButton = findViewById<Button>(R.id.login_button)
        val registerButton = findViewById<Button>(R.id.toregister_button)

        // Initialize the NubbiesClient here
        NubbiesClient.setSharedPreferences(this)

//To Register
        registerButton.setOnClickListener{
            val intent = Intent(this@Login, Register::class.java)
            startActivity(intent)
        }

//Login Function
        loginButton.setOnClickListener{
            val email = inputEmail.text.toString().trim()
            val password = inputPassword.text.toString().trim()

            //JSON data
            val loginDataJson = "{\"email\":\"$email\", \"password\":\"$password\"}"

            try{
                val reader = JsonReader(StringReader(loginDataJson))
                reader.isLenient = true //converts to json format
                reader.beginObject()
                reader.close()

                NubbiesClient.instance.login(email, password)
                    .enqueue(object : Callback<LoginResponse> {
                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            if (response.isSuccessful && response.body() != null){

                                AuthPrefs.storeApiToken(this@Login, response.body()!!.access_token) //grabs API token

                                Toast.makeText(applicationContext, response.body()!!.message, Toast.LENGTH_LONG).show()
                                val intent = Intent(this@Login, MainActivity::class.java)
                                startActivity(intent)
                            }
                            else{
                                val errorMessage: String = try{
                                    response.errorBody()?.string() ?:"failed to get a valid response. Response Code: ${response.code()}"
                                }
                                catch (e: Exception){
                                    "Failed to get a valid response, Response Code: ${response.code()}"
                                }
                                Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_LONG).show()
                                Log.e("API_RESPONSE", errorMessage)
                            }
                        }
                    })
            } catch (e: Exception){
                Toast.makeText(this, "Error parsing json", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

    }
}