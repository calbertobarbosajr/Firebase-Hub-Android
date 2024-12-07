package com.calberto_barbosa_jr.firebasehub.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import com.calberto_barbosa_jr.firebasehub.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Configurando insets para ajuste de bordas
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Exibir ProgressBar
        binding.progressBar.visibility = android.view.View.VISIBLE

        // Iniciar a autenticação após 3 segundos
        mainScope.launch {
            delay(3000)
            abrirAutenticacao()
        }
    }

    private fun abrirAutenticacao() {
        // Ocultar ProgressBar antes de abrir a nova Activity
        binding.progressBar.visibility = android.view.View.GONE
        val i = Intent(this, AuthenticationActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun enableEdgeToEdge() {
        // Configuração edge-to-edge, se necessário
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }

}