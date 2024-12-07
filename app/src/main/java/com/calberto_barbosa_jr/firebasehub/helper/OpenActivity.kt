package com.calberto_barbosa_jr.firebasehub.helper

import android.app.Activity
import android.content.Intent
import com.calberto_barbosa_jr.firebasehub.view.HubActivity

class OpenActivity {

    fun signup( activity: Activity ) {
        val intent = Intent(activity, HubActivity::class.java)
        activity.startActivity(intent)
    }

    fun login( activity: Activity ) {
        val intent = Intent(activity, HubActivity::class.java)
        activity.startActivity(intent)
    }
}