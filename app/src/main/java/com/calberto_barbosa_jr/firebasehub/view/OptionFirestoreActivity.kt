package com.calberto_barbosa_jr.firebasehub.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calberto_barbosa_jr.firebasehub.databinding.ActivityOptionFirestoreBinding

class OptionFirestoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOptionFirestoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOptionFirestoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun buttonFirestoreRead(view: View) {
        val intent = Intent(this, FirestoreReadActivity::class.java)
        startActivity(intent)
    }

    fun buttonFirestoreSave(view: View) {
        val intent = Intent(this, FirestoreSaveActivity::class.java)
        startActivity(intent)
    }
}