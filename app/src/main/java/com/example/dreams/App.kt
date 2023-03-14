package com.example.dreams

import android.app.Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        DreamSingl.instance(this)
    }
}