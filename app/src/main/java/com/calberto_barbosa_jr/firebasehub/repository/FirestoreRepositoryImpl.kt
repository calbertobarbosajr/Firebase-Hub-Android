package com.calberto_barbosa_jr.firebasehub.repository

import com.calberto_barbosa_jr.firebasehub.firestore.FirestoreDocument
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : FirestoreRepository {

    override fun <T : FirestoreDocument> addDocument(
        collection: String,
        documentId: String,
        data: T,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(collection).document(documentId)
            .set(data)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it.message ?: "Unknown error") }
    }

    override fun updateDocument(
        collection: String,
        documentId: String,
        updates: Map<String, Any>,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        if (updates.isEmpty()) {
            onFailure("No fields to update.")
            return
        }

        db.collection(collection).document(documentId)
            .update(updates)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it.message ?: "Unknown error") }
    }

    override fun deleteDocument(
        collection: String,
        documentId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(collection).document(documentId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it.message ?: "Unknown error") }
    }

    override fun <T : FirestoreDocument> fetchDocuments(
        collection: String,
        clazz: Class<T>,
        onSuccess: (List<T>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection(collection).get()
            .addOnSuccessListener { snapshot ->
                val documents = snapshot.documents.mapNotNull { it.toObject(clazz) }
                onSuccess(documents)
            }
            .addOnFailureListener { onFailure(it.message ?: "Unknown error") }
    }
}
