package com.example.dramaproject.api.base

import retrofit2.Response

class ApiException<T>(val response: Response<T>){

    fun catchResponse(){

    }


    fun catchException(){

    }

}