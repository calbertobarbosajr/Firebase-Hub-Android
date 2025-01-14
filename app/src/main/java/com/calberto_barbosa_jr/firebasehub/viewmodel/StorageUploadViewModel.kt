package com.calberto_barbosa_jr.firebasehub.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calberto_barbosa_jr.firebasehub.helper.PermissionManager
import kotlinx.coroutines.launch

/*
class StorageUploadViewModel(
    private val repository: StorageUploadRepository,
    val permissionManager: PermissionManager
) : ViewModel() {


    private val _uploadStatus = MutableLiveData<Boolean>()
    val uploadStatus: LiveData<Boolean> get() = _uploadStatus

    private val _permissionRequestNeeded = MutableLiveData<Boolean>()
    val permissionRequestNeeded: LiveData<Boolean> get() = _permissionRequestNeeded

    private val maxUploadCount = 15

    fun checkPermissions(permissions: Array<String>) {
        if (permissionManager.hasPermissions(permissions)) {
            // Permissões já concedidas, continue com o fluxo de upload
        } else {
            _permissionRequestNeeded.postValue(true)  // Notifica a Activity que precisa solicitar permissões
        }
    }

    fun uploadImages(uid: String, imageUris: List<Uri>) {
        viewModelScope.launch {
            try {
                val imagesToUpload = imageUris.take(maxUploadCount)
                val results = imagesToUpload.mapIndexed { index, uri ->
                    repository.uploadImage(uid, index, uri)
                }

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

 */