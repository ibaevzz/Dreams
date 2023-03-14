package com.example.dreams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuoteViewModel: ViewModel() {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.forismatic.com/")
        .build()
    private val ri = retrofit.create(RI::class.java)

    var quo: LiveData<Dream>

    fun getNewQuote(): LiveData<Dream>{
        val quote = MutableLiveData<Dream>()
        ri.get().enqueue(object: Callback<Dream>{
            override fun onResponse(call: Call<Dream>, response: Response<Dream>) {
                quote.value = response.body()
            }

            override fun onFailure(call: Call<Dream>, t: Throwable) {

            }

        })
        quo = quote
        return quote
    }

    init{
        quo = getNewQuote()
    }
}