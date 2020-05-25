package com.example.ktest.data.net

interface RestCallback<T> {
    fun onResponseData(t: T)
    fun onFailureData(errorMessage: String)
}