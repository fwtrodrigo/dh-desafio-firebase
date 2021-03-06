package com.example.dhdesafiofirebase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dhdesafiofirebase.databinding.ActivitySplashBinding
import com.example.dhdesafiofirebase.helper.appHome
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scope.launch {
            delay(3000)

            val user = FirebaseAuth.getInstance().currentUser?.uid
            if (user != null) {
                appHome(this@SplashActivity)
                finish()
            } else {
                startActivity(Intent(application, LoginActivity::class.java))
                finish()
            }

        }

    }
}