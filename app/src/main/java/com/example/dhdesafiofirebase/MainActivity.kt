package com.example.dhdesafiofirebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dhdesafiofirebase.databinding.ActivityMainBinding
import com.example.dhdesafiofirebase.helper.showToast


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

        binding.tilPesquisa.setStartIconOnClickListener {
            val termo = binding.edtPesquisa.text.toString()


            if (termo.isEmpty()) {
                viewModel.obterJogos()
                return@setStartIconOnClickListener
            }
            Log.d("BODOOWQDOQOWDQODQDQ", "$termo")

            viewModel.pesquisarJogo(termo)
        }

        binding.tilPesquisa.setEndIconOnClickListener {
            showToast(this, "Segure para falar")
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