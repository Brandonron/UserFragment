package com.example.userfragment.api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiInterceptor : Interceptor {
    val access_token: String = ""

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()

        val new: Request = original.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader(
                "Authentication",
                "token " + access_token
            )
            .method(original.method, original.body)
            .build()

        val response = chain.proceed(new)
        when (response.code) {
            400 -> {
                //Show Bad Request Error Message
            }
            401 -> {
                //Show UnauthorizedError Message
            }

            403 -> {
                //Show Forbidden Message
            }

            404 -> {
                //Show NotFound Message
            }

            // ... and so on

        }
        return response
    }
}