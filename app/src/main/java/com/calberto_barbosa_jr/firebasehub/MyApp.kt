package com.calberto_barbosa_jr.firebasehub

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    companion object {
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}
