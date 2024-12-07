package com.calberto_barbosa_jr.firebasehub.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calberto_barbosa_jr.firebasehub.R
import androidx.lifecycle.ViewModelProvider
import android.view.View
import com.calberto_barbosa_jr.firebasehub.databinding.ActivityFirestoreSaveBinding
import com.calberto_barbosa_jr.firebasehub.model.User
import com.calberto_barbosa_jr.firebasehub.viewmodel.FirestoreSaveViewModel

class FirestoreSaveActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirestoreSaveBinding
    private lateinit var viewModel: FirestoreSaveViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFirestoreSaveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this).get(FirestoreSaveViewModel::class.java)
    }

    private fun showMessage(message: String) {
        binding.textViewMessageFirestore.text = message
    }

    private fun getUserFromUI(): User {
        return User(
            binding.editTextNameFirestore.text.toString(),
            binding.editTextCpfFirestore.text.toString(),
            binding.editTextEmailFirestore.text.toString()
        )
    }

    fun buttonSaveFirestore(view: View) {
        val user = getUserFromUI()
        viewModel.addDocumentToFirestore(user,
            onSuccess = { showMessage("Document added successfully!") },
            onFailure = { showMessage(it) }
        )
    }

    fun buttonUpdateFirestore(view: View) {
        val updates = mutableMapOf<String, Any>()

        with(binding) {
            editTextNameFirestore.text.toString().takeIf { it.isNotEmpty() }?.let {
                updates["name"] = it
            }
            editTextCpfFirestore.text.toString().takeIf { it.isNotEmpty() }?.let {
                updates["cpf"] = it
            }
            editTextEmailFirestore.text.toString().takeIf { it.isNotEmpty() }?.let {
                updates["email"] = it
            }
        }

        viewModel.updateDocumentInFirestore(updates,
            onSuccess = { showMessage("Document updated successfully!") },
            onFailure = { showMessage(it) }
        )
    }

    fun buttonDeleteFirestore(view: View) {
        viewModel.deleteDocumentInFirestore(
            onSuccess = { showMessage("Document deleted successfully!") },
            onFailure = { showMessage(it) }
        )
    }

}