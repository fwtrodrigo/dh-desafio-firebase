package com.example.dhdesafiofirebase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dhdesafiofirebase.databinding.ActivityJogoBinding
import com.squareup.picasso.Picasso

class JogoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJogoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarJogo.setNavigationOnClickListener {
            onBackPressed()
        }

        val jogo = intent.getSerializableExtra("jogo") as Jogo

        binding.tvDescricao.text = jogo.descricao
        binding.tvTitulo.text = jogo.titulo
        binding.tvTitulo2.text = jogo.titulo
        binding.tvAnoLancamento.text = jogo.anoLancamento.toString()
        Picasso.get().load(jogo.capa)
            .into(binding.ivCapa)
    }
}