package com.calberto_barbosa_jr.firebasehub.storage

import android.net.Uri

interface UploadStrategy {
    suspend fun upload(file: Uri): Result<String>
}
