package com.calberto_barbosa_jr.firebasehub.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calberto_barbosa_jr.firebasehub.R
import com.calberto_barbosa_jr.firebasehub.databinding.ActivityOptionStorageBinding

class OptionStorageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOptionStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOptionStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun buttonStorageUpload(view: View) {
        val intent = Intent(this, StorageUploadActivity::class.java)
        startActivity(intent)
    }

    fun buttonStorageDownload(view: View) {
        val intent = Intent(this, StorageDownloadActivity::class.java)
        startActivity(intent)
    }

}