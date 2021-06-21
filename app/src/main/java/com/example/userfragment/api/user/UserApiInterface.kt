package com.example.userfragment.api.user

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiInterface  {
    @GET("user")
    fun user(): Call<UserResponse>

    @GET("users")
    fun userList(): Call<UserListResponse>

    @GET("users/{username}")
    fun userInfo(@Path("username") name: String): Call<UserInfoResponse>
}