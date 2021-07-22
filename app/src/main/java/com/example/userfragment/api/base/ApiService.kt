package com.example.dramaproject.api.base

import com.example.userfragment.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class ApiService : ApiParameter {

    protected val okhttpClient: OkHttpClient.Builder by lazy {
        OkHttpClient.Builder()
            .connectTimeout(apiConnectTime(), TimeUnit.SECONDS)
            .readTimeout(apiReadTime(), TimeUnit.SECONDS)
            .writeTimeout(apiWriteTime(), TimeUnit.SECONDS)
            .addInterceptor(ApiInterceptor())
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                )
            )
    }

    protected val retrofitClient: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(apiURL())
            .client(okhttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
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

    protected inline fun <reified T> createAPI(): T = retrofitClient.build().create(T::class.java)
}