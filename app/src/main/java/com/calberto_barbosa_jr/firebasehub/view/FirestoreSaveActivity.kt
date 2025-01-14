package com.calberto_barbosa_jr.firebasehub.view

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calberto_barbosa_jr.firebasehub.R
import com.calberto_barbosa_jr.firebasehub.databinding.ActivityFirestoreSaveBinding

class FirestoreSaveActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirestoreSaveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFirestoreSaveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun buttonSaveFirestore(view: View) {}
    fun buttonUpdateFirestore(view: View) {}
    fun buttonDeleteFirestore(view: View) {}
}