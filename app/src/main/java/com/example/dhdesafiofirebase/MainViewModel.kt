package com.example.dhdesafiofirebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val jogos = MutableLiveData<ArrayList<Jogo>>()

    fun obterJogos() {
        val jogosFirestore = arrayListOf<Jogo>()
        try {
            viewModelScope.launch {
                FirebaseFirestore.getInstance().collection("jogos").get()
                    .addOnSuccessListener { result ->
                        for (jogo in result) {
                            val t = jogo.data["titulo"].toString()
                            val a = jogo.data["anoLancamento"].toString().toInt()
                            val d = jogo.data["descricao"].toString()
                            val c = jogo.data["capa"].toString()

                            val j = Jogo(t, a, d, c)
                            jogosFirestore.add(j)
                        }
                        jogos.value = jogosFirestore
                    }
                    .addOnFailureListener { exception ->
                        Log.w("MAINVIEWMODEL", "Error getting documents : $exception")
                    }
            }
        } catch (e: Exception) {
            throw java.lang.Exception()
        }
    }
}