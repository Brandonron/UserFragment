package com.example.userfragment.api.user

import com.example.userfragment.api.user.response.SearchListResponse
import com.example.userfragment.api.user.response.UserInfoResponse
import com.example.userfragment.api.user.response.UserListResponse
import com.example.userfragment.api.user.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiInterface {
    @GET("user")
    fun user(): Call<UserResponse>

    @GET("users")
    fun userList(): Call<UserListResponse>

    @GET("users/{username}")
    fun userInfo(@Path("username") name: String): Call<UserInfoResponse>

    @GET("search/users")
    fun searchList(@Query("q") name: String?, @Query("per_page") count: Int?,
                   @Query("page") page: Int?): Call<SearchListResponse>
}