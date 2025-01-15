package com.calberto_barbosa_jr.firebasehub.storage

class ProfileImagePathProvider : StoragePathProvider {
    override fun getDirectoryName(): String = "profile_images"
    override fun getFileName(): String = "profile_${System.currentTimeMillis()}.jpg"
}

class ProductImagePathProvider(private val productId: String) : StoragePathProvider {
    override fun getDirectoryName(): String = "products/$productId"
    override fun getFileName(): String = "product_${System.currentTimeMillis()}.jpg"
}
