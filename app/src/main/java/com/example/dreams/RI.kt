package com.example.dreams

import retrofit2.Call
import retrofit2.http.GET

interface RI {
    @GET("https://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=ru")
    fun get(): Call<Dream>
}