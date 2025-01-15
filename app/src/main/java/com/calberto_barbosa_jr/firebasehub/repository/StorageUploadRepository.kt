package com.calberto_barbosa_jr.firebasehub.repository

import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.calberto_barbosa_jr.firebasehub.MyApp
import com.calberto_barbosa_jr.firebasehub.storage.StoragePathProvider
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject

class StorageUploadRepository @Inject constructor(
    private val storage: FirebaseStorage
) {

    // Creates an image file in the specified directory
    fun createImageFile(pathProvider: StoragePathProvider): File? {
        val storageDir = File(
            MyApp.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            pathProvider.getDirectoryName()
        )
        if (!storageDir.exists()) storageDir.mkdirs()
        return File(storageDir, pathProvider.getFileName())
    }

    // Generates a FileProvider URI for the given file
    fun getFileProviderUri(file: File): Uri {
        return FileProvider.getUriForFile(
            MyApp.context,
            "com.calberto_barbosa_jr.firebasehub.fileprovider",
            file
        )
    }

    // Uploads a single image to Firebase Storage
    suspend fun uploadSingleFile(fileUri: Uri, pathProvider: StoragePathProvider): Result<String> {
        return try {
            val fileRef = storage.reference.child("${pathProvider.getDirectoryName()}/${pathProvider.getFileName()}")
            fileRef.putFile(fileUri).await()
            val downloadUrl = fileRef.downloadUrl.await().toString()
            Result.success(downloadUrl)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Uploads multiple images to Firebase Storage
    suspend fun uploadImages(
        imageUris: List<Uri>,
        pathProvider: StoragePathProvider,
        callback: (Result<String>) -> Unit
    ) {
        imageUris.forEach { uri ->
            try {
                val fileRef = storage.reference.child("${pathProvider.getDirectoryName()}/${pathProvider.getFileName()}")
                fileRef.putFile(uri).await()
                val downloadUrl = fileRef.downloadUrl.await().toString()
                callback(Result.success(downloadUrl))
            } catch (e: Exception) {
                callback(Result.failure(e))
            }
        }
    }
}