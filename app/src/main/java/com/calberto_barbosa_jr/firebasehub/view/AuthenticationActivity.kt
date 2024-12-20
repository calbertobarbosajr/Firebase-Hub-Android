package com.calberto_barbosa_jr.firebasehub.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.calberto_barbosa_jr.firebasehub.R
import com.calberto_barbosa_jr.firebasehub.databinding.ActivityAuthenticationBinding
import com.calberto_barbosa_jr.firebasehub.helper.OpenActivity
import com.calberto_barbosa_jr.firebasehub.viewmodel.AuthenticationViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding
    private lateinit var viewModel: AuthenticationViewModel
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this).get(AuthenticationViewModel::class.java)
        createObservers()
    }

    fun login(view: View) {
        val email = binding.editEmailLogin.text.toString()
        val password = binding.editPasswordLogin.text.toString()
        dataValidation(email, password)
    }

    private fun dataValidation(email: String, password: String) {
        if (email.isEmpty()) {
            showError("Enter your email")
        } else if (password.isEmpty()) {
            showError("Enter your password")
        } else {
            viewModel.login(this, email, password)
        }
    }

    private fun showError(message: String) {
        binding.errorMessageLogin.text = message
    }

    private fun createObservers() {
        viewModel.errorMessage().observe(this, Observer {
            showError(it)
        })
    }

    fun signup(view: View) {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }

    fun recover(view: View) {
        val email: String = binding.editEmailLogin.text.toString()

        if (email.isEmpty()) {
            binding.errorMessageLogin.text = "Enter your email \nfor the new password \nto be sent"
            //showToast("Enter your email for the new password to be sent")
        } else {
            sendNewPassword(email)
        }
    }

    private fun sendNewPassword(email: String) {
        auth.sendPasswordResetEmail(email).addOnSuccessListener {
            binding.errorMessageLogin.text = "We sent an email with a link \nto reset your password"
            //showToast("We sent an email with a link to reset your password")
        }.addOnFailureListener {
            binding.errorMessageLogin.text = "Enter valid email"
            //showError("Enter valid email")
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            OpenActivity().login(this)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()
    }

}