package com.calberto_barbosa_jr.firebasehub.repository

import com.calberto_barbosa_jr.firebasehub.firestore.FirestoreDocument

interface FirestoreRepository {
    fun <T : FirestoreDocument> addDocument(
        collection: String,
        documentId: String,
        data: T,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun updateDocument(
        collection: String,
        documentId: String,
        updates: Map<String, Any>,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun deleteDocument(
        collection: String,
        documentId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun <T : FirestoreDocument> fetchDocuments(
        collection: String,
        clazz: Class<T>,
        onSuccess: (List<T>) -> Unit,
        onFailure: (String) -> Unit
    )
}
