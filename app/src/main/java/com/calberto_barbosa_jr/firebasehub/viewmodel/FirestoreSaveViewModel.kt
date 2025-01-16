package com.calberto_barbosa_jr.firebasehub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.calberto_barbosa_jr.firebasehub.firestore.FirestoreDocument
import com.calberto_barbosa_jr.firebasehub.model.User
import com.calberto_barbosa_jr.firebasehub.repository.FirestoreRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FirestoreSaveViewModel @Inject constructor(
    private val repository: FirestoreRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _operationResult = MutableLiveData<Result<Unit>>()
    val operationResult: LiveData<Result<Unit>> get() = _operationResult

    private fun getCurrentUserId(): String? = auth.currentUser?.uid

    fun <T : FirestoreDocument> addDocument(
        data: T,
        collection: String
    ) {
        val userId = getCurrentUserId()
        if (userId == null) {
            _operationResult.value = Result.failure(Exception("No authenticated user."))
            return
        }

        repository.addDocument(
            collection, userId, data,
            onSuccess = { _operationResult.value = Result.success(Unit) },
            onFailure = { _operationResult.value = Result.failure(Exception(it)) }
        )
    }

    fun updateDocument(
        collection: String,
        updates: Map<String, Any>
    ) {
        val userId = getCurrentUserId()
        if (userId == null) {
            _operationResult.value = Result.failure(Exception("No authenticated user."))
            return
        }

        repository.updateDocument(
            collection, userId, updates,
            onSuccess = { _operationResult.value = Result.success(Unit) },
            onFailure = { _operationResult.value = Result.failure(Exception(it)) }
        )
    }

    fun deleteDocument(collection: String) {
        val userId = getCurrentUserId()
        if (userId == null) {
            _operationResult.value = Result.failure(Exception("No authenticated user."))
            return
        }

        repository.deleteDocument(
            collection, userId,
            onSuccess = { _operationResult.value = Result.success(Unit) },
            onFailure = { _operationResult.value = Result.failure(Exception(it)) }
        )
    }

    fun <T : FirestoreDocument> fetchDocuments(
        collection: String,
        clazz: Class<T>,
        onSuccess: (List<T>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        repository.fetchDocuments(collection, clazz, onSuccess, onFailure)
    }
}
