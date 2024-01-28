package com.calberto_barbosa_jr.fireconnectkotlinmvvm.helper

import android.app.Activity
import android.content.Intent
import com.calberto_barbosa_jr.fireconnectkotlinmvvm.activity.MainActivity

class OpenActivity {

    fun signup( activity: Activity ) {
        val intent = Intent(activity, MainActivity::class.java)
        activity.startActivity(intent)
    }

    fun login( activity: Activity ) {
        val intent = Intent(activity, MainActivity::class.java)
        activity.startActivity(intent)
    }
}