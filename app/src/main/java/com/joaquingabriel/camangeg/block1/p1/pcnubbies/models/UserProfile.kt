package com.joaquingabriel.camangeg.block1.p1.pcnubbies.models

data class UserProfile(
    val user: UserProfileDetails
) {
    data class UserProfileDetails(
        val id: Int,
        val name: String?,
        val email: String?,
        val gender: Int,
        val age_range: Int
    )
}
