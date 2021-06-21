package com.example.userfragment.api.user

import com.example.userfragment.api.ApiManager

object UserApiManager : ApiManager() {

    private fun callAPI(): UserApiInterface {
        return initRetrofit(initOkhttpClient()).create(UserApiInterface::class.java)
    }

    fun getUser() = callAPI().user()

    fun getUserList() = callAPI().userList()

    fun getUserInfo(name: String) = callAPI().userInfo(name)
}