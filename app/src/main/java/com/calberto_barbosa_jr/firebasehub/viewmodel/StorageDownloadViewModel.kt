package com.calberto_barbosa_jr.firebasehub.viewmodel

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.calberto_barbosa_jr.firebasehub.model.ImageModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


class StorageDownloadViewModel : ViewModel() {
    private val _imageList = MutableLiveData<List<ImageModel>>()
    val imageList: LiveData<List<ImageModel>> get() = _imageList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun downloadImages(uid: String, storage: FirebaseStorage) {
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
                        val images = downloadTasks.mapNotNull { task ->
                            if (task.isSuccessful) {
                                val uri = task.result as Uri
                                ImageModel(uri.toString())
                            } else {
                                null
                            }
                        }
                        _imageList.postValue(images)
                    }
                    .addOnFailureListener { exception ->
                        _errorMessage.postValue("Download failed: ${exception.message}")
                    }
            }
            .addOnFailureListener { exception ->
                _errorMessage.postValue("Download failed: ${exception.message}")
            }
    }
}
