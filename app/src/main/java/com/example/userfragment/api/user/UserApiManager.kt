package com.example.userfragment.api.user

import com.example.dramaproject.api.base.ApiService
import com.example.userfragment.api.user.response.SearchResponse

object UserApiManager : ApiService() {

    override fun apiURL(): String {
        return "https://api.github.com/"
    }

    fun getUser() = createAPI<UserApiInterface>().user()

    fun getUserList() = createAPI<UserApiInterface>().userList()

    fun getUserInfo(name: String) = createAPI<UserApiInterface>().userInfo(name)

    fun getSearchList(name: String?) =
        createAPI<UserApiInterface>().searchList(name, 100, 1)

    suspend fun getSearch(name: String?, page: Int): SearchResponse =
        createAPI<UserApiInterface>().search(name, 100, page)
}