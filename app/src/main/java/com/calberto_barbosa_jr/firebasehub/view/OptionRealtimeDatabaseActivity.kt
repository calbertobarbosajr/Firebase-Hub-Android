package com.calberto_barbosa_jr.firebasehub.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calberto_barbosa_jr.firebasehub.R
import android.view.View
import com.calberto_barbosa_jr.firebasehub.databinding.ActivityOptionRealtimeDatabaseBinding

class OptionRealtimeDatabaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOptionRealtimeDatabaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOptionRealtimeDatabaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun buttonRealTimeRead(view: View) {
        val intent = Intent(this, RealtimeReadActivity::class.java)
        startActivity(intent)
    }

    fun buttonRealTimeSave(view: View) {
        val intent = Intent(this, RealtimeSaveActivity::class.java)
        startActivity(intent)
    }

}