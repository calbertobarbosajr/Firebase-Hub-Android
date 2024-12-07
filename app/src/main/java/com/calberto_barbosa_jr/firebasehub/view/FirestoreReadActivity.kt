package com.calberto_barbosa_jr.firebasehub.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calberto_barbosa_jr.firebasehub.R
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.calberto_barbosa_jr.firebasehub.databinding.ActivityFirestoreReadBinding
import com.calberto_barbosa_jr.firebasehub.model.User
import com.calberto_barbosa_jr.firebasehub.viewmodel.FirestoreReadViewModel

class FirestoreReadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirestoreReadBinding
    private lateinit var viewModel: FirestoreReadViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFirestoreReadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this).get(FirestoreReadViewModel::class.java)

        viewModel.userLiveData.observe(this, Observer { user ->
            user?.let { displayUserInfo(it) }
        })

        viewModel.errorMessageLiveData.observe(this, Observer { errorMessage ->
            errorMessage?.let { showToast(it) }
        })

        viewModel.readDocument()
    }

    private fun displayUserInfo(user: User) {
        with(binding) {
            textViewNameFirestore.text = "Nome: ${user.name}"
            textViewCpfFirestore.text = "CPF: ${user.cpf}"
            textViewEmailFirestore.text = "E-mail: ${user.email}"
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}