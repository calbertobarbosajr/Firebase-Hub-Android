package com.calberto_barbosa_jr.firebasehub.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calberto_barbosa_jr.firebasehub.R
import android.view.View
import com.calberto_barbosa_jr.firebasehub.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class HubActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun buttonStorage(view: View) {
        val intent = Intent(this, OptionStorageActivity::class.java)
        startActivity(intent)
    }

    fun buttonDatabase(view: View) {
        val intent = Intent(this, OptionRealtimeDatabaseActivity::class.java)
        startActivity(intent)
    }

    fun buttonFirestore(view: View) {
        val intent = Intent(this, OptionFirestoreActivity::class.java)
        startActivity(intent)
    }

    fun buttonExit(view: View) {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, AuthenticationActivity::class.java)
        startActivity(intent)
        finish()
    }

}