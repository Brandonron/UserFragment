package com.example.userfragment.api.base

import com.example.dramaproject.api.base.ApiService

abstract class ApiManager<T> : ApiService() {

    override fun apiURL(): String {
        return "https://api.github.com/"
    }

    override fun apiConnectTime(): Long {
        return 15
    }

    override fun apiReadTime(): Long {
        return 15
    }

    override fun apiWriteTime(): Long {
        return 15
    }

    internal fun createAPI(): T {
        return retrofitClient.build().create(serviceClass())
    }

    abstract fun serviceClass(): Class<T>
}