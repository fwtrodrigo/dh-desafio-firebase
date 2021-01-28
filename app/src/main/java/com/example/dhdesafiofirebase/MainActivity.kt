package com.example.dhdesafiofirebase

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dhdesafiofirebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = JogoAdapter(this::onJogoItemClick)

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycerView()

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, CadastroJogoActivity::class.java))
        }

        viewModel.jogos.observe(this) {
            adapter.jogos = it
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.obterJogos()
    }

    private fun initRecycerView() {
        val layoutManager = GridLayoutManager(this, 2)
        binding.rvJogos.layoutManager = layoutManager
        binding.rvJogos.hasFixedSize()
        binding.rvJogos.adapter = adapter
    }

    private fun onJogoItemClick(jogo: Jogo) {
        startActivity(
            Intent(this, JogoActivity::class.java)
                .putExtra("jogo", jogo)
        )
    }
}