package com.example.dhdesafiofirebase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dhdesafiofirebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val jogos = mutableListOf<Jogo>()
    private val adapter = JogoAdapter(jogos, this::onJogoItemClick)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycerView()

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, CadastroJogoActivity::class.java))
        }
    }

    private fun initRecycerView() {
        binding.rvJogos.adapter = adapter
        val layoutManager = GridLayoutManager(this, 2)

        binding.rvJogos.layoutManager = layoutManager
    }

    private fun addJogo() {
        val jogo = Jogo("Titulo", 1994, "Descricao", "https://www.google.com/image")
        jogos.add(jogo)
        adapter.notifyItemInserted(jogos.lastIndex)
    }

    private fun onJogoItemClick(jogo: Jogo) {
        startActivity(
            Intent(this, JogoActivity::class.java)
                .putExtra("jogo", jogo)
        )
    }
}