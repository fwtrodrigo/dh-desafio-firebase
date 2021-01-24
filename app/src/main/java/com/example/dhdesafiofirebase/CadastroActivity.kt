package com.example.dhdesafiofirebase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dhdesafiofirebase.databinding.ActivityCadastroBinding
import com.example.dhdesafiofirebase.helper.appLogin
import com.example.dhdesafiofirebase.helper.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        binding.btnCreateAccount.setOnClickListener {
            val name = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val passwordRepeate = binding.edtPasswordRepeate.text.toString()

            when {
                password != passwordRepeate -> {
                    showToast(this, "A repeticao deve ser igual a senha")
                }
                password.length < 6 -> {
                    showToast(this, "A senha deve conter pelo menos 6 caracteres")
                }
                else -> {
                    criaUsuario(email, password)
                }
            }
        }
    }

    private fun criaUsuario(email: String, senha: String) {
        mAuth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener {
            }.addOnSuccessListener {
                showToast(this, "Conta criada com sucesso")
                appLogin(this)
            }.addOnFailureListener {
                showToast(this, it.toString())
                if (it is FirebaseAuthUserCollisionException) {
                    showToast(this, "E-mail já utilizado por outra conta")
                }
                if (it is FirebaseAuthInvalidCredentialsException) {
                    showToast(this, "Formato de e-mail inválido")
                }
                if (it is FirebaseAuthWeakPasswordException) {
                    showToast(this, "A senha deve conter pelo menos 6 caracteres")
                }
            }
    }
}