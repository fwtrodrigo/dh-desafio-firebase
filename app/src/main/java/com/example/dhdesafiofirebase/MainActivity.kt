package com.example.dhdesafiofirebase

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dhdesafiofirebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val jogos = mutableListOf<Jogo>()
    private val adapter = JogoAdapter(jogos, this::onJogoItemClick)
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycerView()

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, CadastroJogoActivity::class.java))
        }

        viewModel.lJogo.observe(this) {
            addJogo(it)
        }

        viewModel.obterJogos()
    }

    private fun initRecycerView() {
        binding.rvJogos.adapter = adapter
        val layoutManager = GridLayoutManager(this, 2)

        binding.rvJogos.layoutManager = layoutManager
    }

    private fun addJogo(jogo: Jogo) {
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