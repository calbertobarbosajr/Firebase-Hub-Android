package com.calberto_barbosa_jr.firebasehub.storage

interface StoragePathProvider {
    fun getDirectoryName(): String
    fun getFileName(): String
}
