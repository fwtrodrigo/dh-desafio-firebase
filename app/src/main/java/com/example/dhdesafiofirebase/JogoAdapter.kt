package com.example.dhdesafiofirebase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dhdesafiofirebase.databinding.ItemJogoBinding
import com.squareup.picasso.Picasso

class JogoAdapter( val jogos: MutableList<Jogo>, private val callback: (Jogo) -> Unit) :
    RecyclerView.Adapter<JogoAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JogoAdapter.VH {
        val binding = ItemJogoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val vh = VH(binding)

        vh.itemView.setOnClickListener {
            val jogo = jogos[vh.adapterPosition]
            callback(jogo)
        }

        return vh
    }

    override fun onBindViewHolder(holder: JogoAdapter.VH, position: Int) {
        with(holder) {
            with(jogos[position]) {
                binding.tvTitulo.text = this.titulo
                binding.tvAnoLancamento.text = this.anoLancamento.toString()
                Picasso.get().load(this.capa)
                    .into(binding.imgCapa)
            }
        }
    }

    override fun getItemCount(): Int = jogos.size

    inner class VH(val binding: ItemJogoBinding) : RecyclerView.ViewHolder(binding.root)
}