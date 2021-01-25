package com.example.dhdesafiofirebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val lJogo = MutableLiveData<Jogo>()
    fun obterJogos() {
        try {
            val respJogos = mutableListOf<Jogo>()
            viewModelScope.launch {
                FirebaseFirestore.getInstance().collection("jogos").get()
                    .addOnSuccessListener { result ->
                        for (jogo in result) {
                            val t = jogo.data["titulo"].toString()
                            val a = jogo.data["anoLancamento"].toString().toInt()
                            val d = jogo.data["descricao"].toString()
                            val c = jogo.data["capa"].toString()

                            val game = Jogo(t, a, d, c)
                            lJogo.value = game
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.w("MAINVIEWMODEL", "Error getting documents : $exception")
                    }

            }

            Log.d("MAINVIEWMODEL&&&&&&&&&&&&&&&&", "$lJogo")

        } catch (e: Exception) {
            throw java.lang.Exception()
        }
    }
}