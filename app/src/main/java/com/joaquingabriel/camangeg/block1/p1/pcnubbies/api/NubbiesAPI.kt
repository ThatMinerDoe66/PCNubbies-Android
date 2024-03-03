package com.joaquingabriel.camangeg.block1.p1.pcnubbies.api

import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.DefaultResponse
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface NubbiesAPI {
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("register") //register here
    fun createUser(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("gender") gender:Int,
        @Field("age_range") ageRange:Int,
        @Field("password") password:String
    ): Call<DefaultResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password:String
    ): Call<LoginResponse>

}