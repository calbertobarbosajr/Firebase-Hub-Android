package com.calberto_barbosa_jr.fireconnectkotlinmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.calberto_barbosa_jr.fireconnectkotlinmvvm.repository.StorageUploadRepository

class StorageUploadViewModelFactory(private val repository: StorageUploadRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StorageUploadViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StorageUploadViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}