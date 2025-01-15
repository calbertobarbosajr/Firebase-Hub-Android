package com.calberto_barbosa_jr.firebasehub.storage

import android.net.Uri
import com.calberto_barbosa_jr.firebasehub.repository.StorageUploadRepository

class ProductImageUploadStrategy(
    private val repository: StorageUploadRepository,
    private val productId: String
) : UploadStrategy {
    override suspend fun upload(file: Uri): Result<String> {
        val pathProvider = ProductImagePathProvider(productId)
        return repository.uploadSingleFile(file, pathProvider)
    }
}
