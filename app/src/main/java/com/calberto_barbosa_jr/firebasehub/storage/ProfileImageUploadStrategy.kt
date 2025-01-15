package com.calberto_barbosa_jr.firebasehub.storage

import android.net.Uri
import com.calberto_barbosa_jr.firebasehub.repository.StorageUploadRepository

class ProfileImageUploadStrategy(
    private val repository: StorageUploadRepository
) : UploadStrategy {
    override suspend fun upload(file: Uri): Result<String> {
        val pathProvider = ProfileImagePathProvider()
        return repository.uploadSingleFile(file, pathProvider)
    }
}
