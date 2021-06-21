package com.example.userfragment.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class ApiManager {
    private val apiURL = "https://api.github.com/"

    protected fun initOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original: Request = chain.request()

                    val new: Request = original.newBuilder()
                        .addHeader("Authorization","")
                        .addHeader("Content-Type", "application/json")
                        .method(original.method(), original.body())
                        .build()

                    return chain.proceed(new);
                }

            })
            .build()
    }

    protected fun initRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(apiURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}