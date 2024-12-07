package com.calberto_barbosa_jr.firebasehub.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calberto_barbosa_jr.firebasehub.R
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.calberto_barbosa_jr.firebasehub.databinding.ActivityRegistrationBinding
import com.calberto_barbosa_jr.firebasehub.viewmodel.RegistrationViewModel

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var viewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)
        createObservers()
    }

    fun registration(view: View) {
        val email = binding.editEmailRegister.text.toString()
        val password = binding.editPasswordRegister.text.toString()
        val repeatPassword = binding.editRepeatPassword.text.toString()
        dataValidation(email, password, repeatPassword)
    }

    private fun dataValidation(email: String, password: String, repeatPassword: String) {
        when {
            email.isEmpty() -> showError("Enter your email")
            password.isEmpty() -> showError("Enter your password")
            repeatPassword.isEmpty() -> showError("Repeat your password")
            password != repeatPassword -> showError("The passwords you entered are different")
            else -> viewModel.createUser(this, email, password)
        }
    }

    private fun showError(message: String) {
        binding.errorMessageRegister.text = message
    }

    private fun createObservers() {
        viewModel.errorMessage().observe(this, Observer {
            showError(it)
        })
    }
}