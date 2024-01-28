package com.calberto_barbosa_jr.fireconnectkotlinmvvm.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.calberto_barbosa_jr.fireconnectkotlinmvvm.adapter.ImageAdapter
import com.calberto_barbosa_jr.fireconnectkotlinmvvm.databinding.ActivityStorageDownloadBinding
import com.calberto_barbosa_jr.fireconnectkotlinmvvm.viewmodel.StorageDownloadViewModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
/*
class StorageDownloadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStorageDownloadBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter
    private val imageList = mutableListOf<ImageModel>()
    private val storage = Firebase.storage
    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorageDownloadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        imageAdapter = ImageAdapter(this, imageList, storage)
        recyclerView.adapter = imageAdapter

        currentUser?.let { user ->
            val uid: String = user.uid
            val reference = storage.reference.child("images").child(uid)

            reference.listAll()
                .addOnSuccessListener { result ->
                    val downloadTasks = mutableListOf<Task<Uri>>()

                    result.items.forEach { imageRef ->
                        val task = imageRef.downloadUrl
                        downloadTasks.add(task)
                    }

                    Tasks.whenAllComplete(downloadTasks)
                        .addOnSuccessListener {
                            downloadTasks.forEachIndexed { index, task ->
                                if (task.isSuccessful) {
                                    val uri = task.result as Uri
                                    val imageUrl = uri.toString()
                                    imageList.add(ImageModel(imageUrl))
                                    imageAdapter.notifyItemInserted(index)
                                } else {
                                    // Tratar falha ao obter URL
                                }
                            }
                        }
                        .addOnFailureListener { exception ->
                            // Tratar falha geral
                            Toast.makeText(
                                this,
                                "Download failed: ${exception.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Download failed: ${exception.message}", Toast.LENGTH_LONG)
                        .show()
                }
        }
    }
}

 */


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
        binding = ActivityStorageDownloadBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
