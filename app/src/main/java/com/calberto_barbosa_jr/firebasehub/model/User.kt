package com.calberto_barbosa_jr.firebasehub.model

import com.calberto_barbosa_jr.firebasehub.firestore.FirestoreDocument

data class User(
    val name: String = "",
    val cpf: String = "",
    val email: String = ""
) : FirestoreDocument

data class Product(
    val name: String = "",
    val price: Double = 0.0
) : FirestoreDocument
