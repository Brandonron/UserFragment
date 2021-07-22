package com.example.dramaproject.api.base

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface ApiCallBack<T> : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            onApiSuccess(call, response?.body())
        } else {
            onApiFailed(call, response?.errorBody())
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        onApiTimeOut(call, t)
    }

    fun onApiSuccess(call: Call<T>, body: T?)

    fun onApiFailed(call: Call<T>, errorBody: ResponseBody?)

    fun onApiTimeOut(call: Call<T>, t: Throwable)
}