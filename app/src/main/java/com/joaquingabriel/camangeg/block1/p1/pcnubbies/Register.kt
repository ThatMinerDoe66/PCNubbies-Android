package com.joaquingabriel.camangeg.block1.p1.pcnubbies

import android.annotation.SuppressLint
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
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.api.NubbiesClient
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.DefaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.StringReader

class Register : AppCompatActivity() {
    private lateinit var radioButton: RadioButton


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val inputName = findViewById<EditText>(R.id.input_name)
        val inputEmail = findViewById<EditText>(R.id.input_email)
        val inputPassword = findViewById<EditText>(R.id.input_password)
        val inputConfirmPassword = findViewById<EditText>(R.id.input_confirmPassword)

        val registerButton = findViewById<Button>(R.id.register_button)
        var currentGender = 0
        val age = arrayOf("18-24","25-34", "35-45","46-59", "60+")
        val ageSpinner:Spinner = findViewById(R.id.age_range_spinner)

        val radioGroup: RadioGroup = findViewById(R.id.radio_group)
        radioGroup.setOnCheckedChangeListener { group, checkedID ->
            val radioButton: RadioButton = group.findViewById(checkedID)
            when(radioButton.id){
                R.id.option_male -> {
                    currentGender = 0 // Assign "Male" to currentGender
                }
                R.id.option_female -> {
                    currentGender = 1 // Assign "Female" to currentGender
                }
            }
        }
        val ageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, age)
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ageSpinner.adapter = ageAdapter

        ageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedGender = parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }





//Register Function
        registerButton.setOnClickListener{
            val name = inputName.text.toString().trim()
            val email = inputEmail.text.toString().trim()
            val age_group = ageSpinner.selectedItem.toString()
            var currentAge:Int = 0
            when(age_group){
                "18-24" -> currentAge = 1
                "25-34" -> currentAge = 2
                "35-45" -> currentAge = 3
                "45-59" -> currentAge = 4
                "60+" -> currentAge = 5
                else -> currentAge = 0
            }

            val gender = currentGender
            val password = inputPassword.text.toString().trim()
            val confirmPassword = inputConfirmPassword.text.toString().trim()

            //JSON data
            val registerDataJson = "{\"name\":\"$name\",\"email\":\"$email\", \"gender\":\"$gender\", \"age_range\":\"$currentAge\", \"password\":\"$password\"}"

            if (password == confirmPassword){
                try{
                    val reader = JsonReader(StringReader(registerDataJson))
                    reader.isLenient = true //converts to json format
                    reader.beginObject()
                    reader.close()

                    NubbiesClient.instance.createUser(name, email, gender,currentAge, password)
                        .enqueue(object : Callback<DefaultResponse> {
                            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                            }

                            override fun onResponse(
                                call: Call<DefaultResponse>,
                                response: Response<DefaultResponse>
                            ) {
                                if (response.isSuccessful && response.body() != null){
                                    Toast.makeText(applicationContext, response.body()!!.message, Toast.LENGTH_LONG).show()
                                    val intent = Intent(this@Register, Login::class.java)
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
            } else if (password != confirmPassword){
                Toast.makeText(applicationContext, "Password does not match!", Toast.LENGTH_LONG).show()
            }
        }

    }
}