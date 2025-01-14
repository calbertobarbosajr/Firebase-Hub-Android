package com.calberto_barbosa_jr.firebasehub.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calberto_barbosa_jr.firebasehub.R
import com.calberto_barbosa_jr.firebasehub.databinding.ActivityRegistrationBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var repeatPasswordInput: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        // Inicializando o Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Ligando os componentes do layout aos objetos no código
        emailInput = findViewById(R.id.editEmailRegister)
        passwordInput = findViewById(R.id.editPasswordRegister)
        repeatPasswordInput = findViewById(R.id.editRepeatPassword)
    }

    // Método acionado pelo botão de registro
    fun registration(view: View) {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString()
        val repeatPassword = repeatPasswordInput.text.toString()

        // Validação dos campos
        if (email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            showErrorMessage("Todos os campos devem ser preenchidos.")
            return
        }

        if (password != repeatPassword) {
            showErrorMessage("As senhas não coincidem.")
            return
        }

        if (password.length < 6) {
            showErrorMessage("A senha deve ter pelo menos 6 caracteres.")
            return
        }

        // Criar conta com Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sucesso: Usuário registrado
                    Log.d("RegistrationActivity", "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                    Toast.makeText(this, "Usuário registrado com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    // Falha: Tratar possíveis erros
                    handleRegistrationError(task.exception)
                }
            }
    }

    // Exibe mensagens de erro na interface
    private fun showErrorMessage(message: String) {
        val errorMessageTextView = binding.errorMessageRegister
        errorMessageTextView.text = message
    }

    // Trata erros de autenticação
    private fun handleRegistrationError(exception: Exception?) {
        val message = when (exception) {
            is FirebaseAuthWeakPasswordException -> "A senha é muito fraca."
            is FirebaseAuthInvalidCredentialsException -> "E-mail inválido."
            is FirebaseAuthUserCollisionException -> "Este e-mail já está registrado."
            is FirebaseNetworkException -> "Sem conexão com a internet."
            else -> "Erro ao registrar o usuário. Tente novamente."
        }
        showErrorMessage(message)
        Log.w("RegistrationActivity", "createUserWithEmail:failure", exception)
    }

    // Atualiza a interface do usuário
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(this, "Bem-vindo, ${user.email}!", Toast.LENGTH_SHORT).show()
            // Você pode redirecionar para outra Activity ou atualizar o layout
        } else {
            showErrorMessage("Erro ao autenticar o usuário.")
        }
    }
}
