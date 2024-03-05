package com.joaquingabriel.camangeg.block1.p1.pcnubbies.api

import ProductResponse
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.DefaultResponse
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.LoginResponse

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface NubbiesAPI {
    //Cart
    @GET("products")
    suspend fun getProductList(
        @Query("title") title: String?,
        @Query("price") price: Double?
    ): Response<ProductResponse>

    //Register
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


    //Login
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password:String
    ): Call<LoginResponse>


}