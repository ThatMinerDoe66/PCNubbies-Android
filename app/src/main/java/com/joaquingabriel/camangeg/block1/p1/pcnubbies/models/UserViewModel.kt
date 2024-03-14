package com.joaquingabriel.camangeg.block1.p1.pcnubbies.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    private val _userData = MutableLiveData<LoginResponse.UserProfile>()
    val userData: LiveData<LoginResponse.UserProfile> = _userData

    fun setUserData(userProfile: LoginResponse.UserProfile) {
        _userData.value = userProfile
        Log.d("UserViewModel", "userData updated to: ${_userData.value?.name}, ${_userData.value?.email}")
    }
}
