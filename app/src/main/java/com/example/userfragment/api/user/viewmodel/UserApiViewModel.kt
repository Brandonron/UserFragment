package com.example.userfragment.api.user.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.userfragment.api.user.UserApiManager
import com.example.userfragment.api.user.UserListResponse
import com.example.userfragment.api.user.UserInfoResponse
import com.example.userfragment.api.user.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserApiViewModel(private val apiManager: UserApiManager)  : ViewModel(){

    val errorMessage = MutableLiveData<String>()

    val user = MutableLiveData<UserResponse>()

    val userInfo = MutableLiveData<UserInfoResponse>()

    val userList = MutableLiveData<UserListResponse>()

    fun getUser() {
        UserApiManager.getUser()
            .enqueue(object : Callback<UserResponse> {
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    errorMessage.postValue(t.message)
                }

                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    Log.i("bear",response.body().toString())
                    user.postValue(response.body())
                }
            })
    }

    fun getUserList() {
        UserApiManager.getUserList()
            .enqueue(object : Callback<UserListResponse> {
            override fun onFailure(call: Call<UserListResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

            override fun onResponse(
                call: Call<UserListResponse>,
                response: Response<UserListResponse>
            ) {
                userList.postValue(response.body())
            }
        })
    }

    fun getUserInfo(name: String) {
        UserApiManager.getUserInfo(name)
            .enqueue(object : Callback<UserInfoResponse> {
                override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                    errorMessage.postValue(t.message)
                }

                override fun onResponse(
                    call: Call<UserInfoResponse>,
                    response: Response<UserInfoResponse>
                ) {
                    userInfo.postValue(response.body())
                }
            })
    }
}