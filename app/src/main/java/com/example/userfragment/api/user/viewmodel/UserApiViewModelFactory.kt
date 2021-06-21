package com.example.userfragment.api.user.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.userfragment.api.user.UserApiManager

class UserApiViewModelFactory constructor(private val apiManager: UserApiManager) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(UserApiViewModel::class.java)) {
            UserApiViewModel(this.apiManager) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}