package com.belutrac.challengefinal.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.belutrac.challengefinal.R
import com.belutrac.challengefinal.databinding.ActivityLoginBinding
import com.belutrac.challengefinal.main.MainActivity
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        val etMail = binding.etMail
        val etPassword = binding.etPass
        val btnLogin = binding.btnLogin

        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        viewModel.state.observe(this) { state ->
            when {
                (!state.loginError && state.user != null) -> { // Usuario logueado
                    onLogged(state.user)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                (!state.loginError && state.user == null) -> { // Usuario sin loguear o recien deslogueado
                }
                (state.loginError) -> { // El usuario intentó iniciar sesión y falló
                    Toast.makeText(
                        this,
                        getString(R.string.Invalid_user_credentials),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        btnLogin.setOnClickListener {
            // En esta demo, el botón tiene 2 comportamientos dependiendo del estado de la sesión.
            val mail = etMail.text.toString()
            val pass = etPassword.text.toString()
            val enviado = viewModel.login(mail, pass)
            if (!enviado) Toast.makeText(
                this,
                "Ingrese usuario y contraseña",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun onLogged(user: FirebaseUser) {
        user.apply {
            email?.let { Log.d("login", it) }
            isEmailVerified.let { Log.d("login", it.toString()) }
            uid.let { Log.d("login", it) }
        }
    }
}