package com.calberto_barbosa_jr.firebasehub.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calberto_barbosa_jr.firebasehub.R
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.calberto_barbosa_jr.firebasehub.databinding.ActivityRealtimeReadBinding
import com.calberto_barbosa_jr.firebasehub.model.User
import com.calberto_barbosa_jr.firebasehub.viewmodel.RealtimeReadViewModel

class RealtimeReadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRealtimeReadBinding
    private lateinit var viewModel: RealtimeReadViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRealtimeReadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this).get(RealtimeReadViewModel::class.java)

        // Observa as atualizações no usuário e na mensagem de erro
        viewModel.userLiveData.observe(this, Observer { user ->
            updateUI(user)
        })

        viewModel.errorMessageLiveData.observe(this, Observer { errorMessage ->
            showToast(errorMessage)
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun updateUI(user: User) {
        with(binding) {
            textViewNameRealtime.text = "Nome: ${user.name}"
            textViewCpfRealtime.text = "CPF: ${user.cpf}"
            textViewEmailRealtime.text = "E-mail: ${user.email}"
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.attachDatabaseListener()
    }

    override fun onStop() {
        super.onStop()
        viewModel.detachDatabaseListener()
    }
}