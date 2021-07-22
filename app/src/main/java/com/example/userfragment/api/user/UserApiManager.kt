package com.example.userfragment.api.user

import com.example.userfragment.api.base.ApiManager

object UserApiManager : ApiManager<UserApiInterface>() {

    override fun serviceClass(): Class<UserApiInterface> {
        return UserApiInterface::class.java
    }

    fun getUser() = createAPI().user()

    fun getUserList() = createAPI().userList()

    fun getUserInfo(name: String) = createAPI().userInfo(name)
}