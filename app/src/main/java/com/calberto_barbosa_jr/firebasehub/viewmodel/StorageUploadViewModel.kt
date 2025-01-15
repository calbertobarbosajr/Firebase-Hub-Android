package com.calberto_barbosa_jr.firebasehub.viewmodel

import android.net.Uri
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calberto_barbosa_jr.firebasehub.storage.UIState
import com.calberto_barbosa_jr.firebasehub.repository.StorageUploadRepository
import com.calberto_barbosa_jr.firebasehub.storage.StoragePathProvider
import com.calberto_barbosa_jr.firebasehub.storage.UploadStrategy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StorageUploadViewModel @Inject constructor(
    private val repository: StorageUploadRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<UIState>()
    val uiState: LiveData<UIState> = _uiState

    private val imageUris = mutableSetOf<Uri>()
    var selectedImageView: ImageView? = null
        private set
    private var currentUri: Uri? = null

    fun onImageSelected(uri: Uri) {
        currentUri = uri
        imageUris.add(uri)
        _uiState.value = UIState.ImageLoaded(uri)
    }

    fun onImageCaptured() {
        currentUri?.let {
            imageUris.add(it)
            _uiState.value = UIState.ImageLoaded(it)
        } ?: run {
            _uiState.value = UIState.Error("Failed to process the captured image")
        }
    }

    fun onImageSelectedForDisplay(imageView: ImageView) {
        selectedImageView = imageView
    }

    fun openCamera(cameraLauncher: ActivityResultLauncher<Uri>, pathProvider: StoragePathProvider) {
        currentUri = repository.createImageFile(pathProvider)?.let { file ->
            repository.getFileProviderUri(file)
        }
        currentUri?.let { cameraLauncher.launch(it) } ?: run {
            _uiState.value = UIState.Error("Failed to create file for the image")
        }
    }

    fun uploadImages(pathProvider: StoragePathProvider) {
        if (imageUris.isEmpty()) {
            _uiState.value = UIState.Error("No images selected")
            return
        }

        viewModelScope.launch {
            repository.uploadImages(imageUris.toList(), pathProvider) { result ->
                result.onSuccess { imageUrl ->
                    _uiState.value = UIState.UploadSuccess(imageUrl)
                }.onFailure {
                    _uiState.value = UIState.Error("Upload failed: ${it.message}")
                }
            }
        }
    }
}