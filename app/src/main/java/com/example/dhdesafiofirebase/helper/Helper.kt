package com.example.dhdesafiofirebase.helper;

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.dhdesafiofirebase.MainActivity

fun showToast(context: Context, msg: String) {
    Toast.makeText(
        context,
        msg,
        Toast.LENGTH_SHORT
    ).show()
}

fun appLogin(c1: Context) {
    c1.startActivity(Intent(c1, MainActivity::class.java))
}