package com.calberto_barbosa_jr.firebasehub.view

import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calberto_barbosa_jr.firebasehub.R
import com.calberto_barbosa_jr.firebasehub.databinding.ActivityFirestoreSaveBinding
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.calberto_barbosa_jr.firebasehub.model.User
import com.calberto_barbosa_jr.firebasehub.viewmodel.FirestoreSaveViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import org.koin.androidx.viewmodel.ext.android.viewModel

@AndroidEntryPoint
class FirestoreSaveActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirestoreSaveBinding
    private val viewModel: FirestoreSaveViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirestoreSaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupUI()
    }

    private fun setupObservers() {
        viewModel.operationResult.observe(this) { result ->
            result.onSuccess { showMessage("Operation completed successfully!") }
                .onFailure { showMessage(it.message ?: "Unknown error") }
        }
    }

    private fun setupUI() {
        binding.buttonSave.setOnClickListener {
            val user = getUserFromUI()
            viewModel.addDocument(user, "users")
        }

        binding.buttonUpdate.setOnClickListener {
            val updates = getUpdatesFromUI()
            viewModel.updateDocument("users", updates)
        }

        binding.buttonDelete.setOnClickListener {
            viewModel.deleteDocument("users")
        }
    }

    private fun getUserFromUI(): User {
        return User(
            name = binding.editTextNameFirestore.text.toString(),
            cpf = binding.editTextCpfFirestore.text.toString(),
            email = binding.editTextEmailFirestore.text.toString()
        )
    }

    private fun getUpdatesFromUI(): Map<String, Any> {
        val updates = mutableMapOf<String, Any>()
        binding.editTextNameFirestore.text.toString().takeIf { it.isNotEmpty() }?.let { updates["name"] = it }
        binding.editTextCpfFirestore.text.toString().takeIf { it.isNotEmpty() }?.let { updates["cpf"] = it }
        binding.editTextEmailFirestore.text.toString().takeIf { it.isNotEmpty() }?.let { updates["email"] = it }
        return updates
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
