package com.joaquingabriel.camangeg.block1.p1.pcnubbies.api

import ProductResponse
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.AddToCartRequest
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.CartResponse
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.DefaultResponse
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.LoginResponse
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.UserProfile

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface NubbiesAPI {
    //Product
    @GET("products")
    suspend fun getProductList(
        @Query("title") title: String?,
        @Query("price") price: Double?
    ): Response<ProductResponse>

    //Cart
    @GET("carts")
    suspend fun getCartItems(): Response<CartResponse>

    //Adds to Cart
    @POST("cart/{product_id}")
    suspend fun addToCart(
        @Path("product_id") productId: Int,
        @Body request: AddToCartRequest
    ): Response<DefaultResponse>

    //Deletes from Cart
    @DELETE("cart/{product_id}")
    suspend fun removeFromCart(
        @Path("product_id") productId: Int
    ): Response<DefaultResponse>


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

    //User Data
    @GET("user")
    suspend fun fetchUserProfile(
        @Header("Authorization") bearerToken: String
    ): Response<UserProfile>
}
