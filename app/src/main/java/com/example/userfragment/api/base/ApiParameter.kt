package com.example.dramaproject.api.base

interface ApiParameter {

    fun apiURL(): String

    fun apiConnectTime(): Long

    fun apiReadTime(): Long

    fun apiWriteTime(): Long
}