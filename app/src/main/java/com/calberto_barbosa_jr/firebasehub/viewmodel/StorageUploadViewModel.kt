package com.calberto_barbosa_jr.firebasehub.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calberto_barbosa_jr.firebasehub.repository.StorageUploadRepository
import kotlinx.coroutines.launch

class StorageUploadViewModel(private val repository: StorageUploadRepository) : ViewModel() {

    private val _uploadStatus = MutableLiveData<Boolean>()
    val uploadStatus: LiveData<Boolean>
        get() = _uploadStatus

    private val maxUploadCount = 15

    fun uploadImages(uid: String, imageUris: List<Uri>) {
        viewModelScope.launch {
            try {
                val imagesToUpload = imageUris.take(maxUploadCount)

                val results = imagesToUpload.mapIndexed { index, uri ->
                    repository.uploadImage(uid, index, uri)
                }

                // Check if all images were uploaded successfully
                if (results.all { it != null }) {
                    _uploadStatus.postValue(true)
                } else {
                    _uploadStatus.postValue(false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uploadStatus.postValue(false)
            }
        }
    }
}
