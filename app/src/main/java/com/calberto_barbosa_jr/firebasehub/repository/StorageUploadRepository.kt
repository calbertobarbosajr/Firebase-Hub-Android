package com.calberto_barbosa_jr.firebasehub.repository

import android.content.Context
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.io.IOException

/*
class StorageUploadRepository(
    private val storage: FirebaseStorage,
    private val context: Context,
    private val imageNamingStrategy: ImageNamingStrategy // Recebe a estratégia como parâmetro
) {
    suspend fun uploadImage(uid: String, index: Int, uri: Uri): String? {
        try {
            val reference = storage.reference.child("images").child(uid)
            val nomeImagem = reference.child(imageNamingStrategy.generateImageName(index))

            val bytes = ByteArrayOutputStream()
            val inputStream = context.contentResolver.openInputStream(uri)
            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (inputStream!!.read(buffer).also { bytesRead = it } != -1) {
                bytes.write(buffer, 0, bytesRead)
            }

            bytes.close()
            inputStream.close()

            val byteArray = bytes.toByteArray()

            val uploadTask = nomeImagem.putBytes(byteArray)
            val downloadUri = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                nomeImagem.downloadUrl
            }.await()

            return downloadUri.toString()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}

 */