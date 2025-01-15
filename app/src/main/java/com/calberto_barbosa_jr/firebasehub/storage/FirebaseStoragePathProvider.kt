package com.calberto_barbosa_jr.firebasehub.storage

class FirebaseStoragePathProvider(private val directory: String) : StoragePathProvider {
    override fun getDirectoryName(): String = directory
    override fun getFileName(): String = "IMG_${System.currentTimeMillis()}.jpg"
}
