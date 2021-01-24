package com.example.dhdesafiofirebase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dhdesafiofirebase.databinding.ActivityLoginBinding
import com.example.dhdesafiofirebase.helper.appLogin
import com.example.dhdesafiofirebase.helper.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
    }

    private fun signinWithEmail() {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
            }.addOnSuccessListener {
                showToast(
                    this,
                    "Bem vindo, Nome: ${it.user?.displayName} Email: ${it.user?.email} UserID: ${it.user?.uid} !"
                )
                appLogin(this)
                finish()
            }.addOnFailureListener {
                if (it is FirebaseAuthInvalidCredentialsException) {
                    showToast(this, "Usuário e/ou senha inválidos.")
                }

                if (it is FirebaseAuthInvalidUserException) {
                    showToast(this, "Usuário e/ou senha inválidos.")
                }
            }
    }
}