package com.joaquingabriel.camangeg.block1.p1.pcnubbies.models

import com.google.gson.annotations.SerializedName
data class DefaultResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String
)
