package com.calberto_barbosa_jr.firebasehub.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calberto_barbosa_jr.firebasehub.R
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.view.View
import com.calberto_barbosa_jr.firebasehub.databinding.ActivityRealtimeSaveBinding
import com.calberto_barbosa_jr.firebasehub.model.User
import com.calberto_barbosa_jr.firebasehub.viewmodel.RealtimeViewModel

class RealtimeSaveActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRealtimeSaveBinding
    private lateinit var viewModel: RealtimeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRealtimeSaveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this).get(RealtimeViewModel::class.java)

        // Observa as atualizações na mensagem e atualiza a interface do usuário
        viewModel.messageLiveData.observe(this, Observer {
            showMessage(it)
        })
    }

    private fun showMessage(message: String) {
        binding.textViewMessageRealtime.text = message
    }

    private fun getUserFromInputFields(): User {
        with(binding) {
            val name = editTextNameRealtime.text.toString()
            val cpf = editTextCpfRealtime.text.toString()
            val email = editTextEmailRealtime.text.toString()
            return User(name, cpf, email)
        }
    }

    private fun getFieldUpdates(): Map<String, Any> {
        val updates = mutableMapOf<String, Any>()
        with(binding) {
            editTextNameRealtime.text.toString().takeIf { it.isNotEmpty() }?.let {
                updates["name"] = it
            }
            editTextCpfRealtime.text.toString().takeIf { it.isNotEmpty() }?.let {
                updates["cpf"] = it
            }
            editTextEmailRealtime.text.toString().takeIf { it.isNotEmpty() }?.let {
                updates["email"] = it
            }
        }
        return updates
    }

    fun buttonSaveFirestore(view: View) {
        val user = getUserFromInputFields()
        viewModel.addDocumentToRealtimeDatabase(user)
    }

    fun buttonUpdateFirestore(view: View) {
        val updates = getFieldUpdates()
        viewModel.updateDocumentInRealtimeDatabase(updates)
    }

    fun buttonDeleteFirestore(view: View) {
        viewModel.deleteDocumentInRealtimeDatabase()
    }
}