package com.joaquingabriel.camangeg.block1.p1.pcnubbies.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("user") val user: UserProfile
) {
    data class UserProfile(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("email") val email: String,
        @SerializedName("gender") val gender: Int,
        @SerializedName("age_range") val ageRange: Int
    )
}
