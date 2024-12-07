package com.calberto_barbosa_jr.firebasehub.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calberto_barbosa_jr.firebasehub.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.calberto_barbosa_jr.firebasehub.adapter.ImageAdapter
import com.calberto_barbosa_jr.firebasehub.databinding.ActivityStorageDownloadBinding
import com.calberto_barbosa_jr.firebasehub.viewmodel.StorageDownloadViewModel

class StorageDownloadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStorageDownloadBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter
    private val storage = Firebase.storage
    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser

    private val viewModel by lazy {
        ViewModelProvider(this).get(StorageDownloadViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityStorageDownloadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        imageAdapter = ImageAdapter(this, storage)
        recyclerView.adapter = imageAdapter

        currentUser?.let { user ->
            val uid: String = user.uid
            viewModel.downloadImages(uid, storage)

            viewModel.imageList.observe(this, Observer { images ->
                imageAdapter.setImageList(images)
            })

            viewModel.errorMessage.observe(this, Observer { errorMessage ->
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            })
        }
    }
}