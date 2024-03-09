package com.joaquingabriel.camangeg.block1.p1.pcnubbies.models

import com.google.gson.annotations.SerializedName
data class LoginResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String,
    val access_token: String

)
