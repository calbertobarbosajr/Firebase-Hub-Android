package com.calberto_barbosa_jr.fireconnectkotlinmvvm.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.calberto_barbosa_jr.fireconnectkotlinmvvm.databinding.ActivityOptionStorageBinding

class OptionStorageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOptionStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOptionStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)
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