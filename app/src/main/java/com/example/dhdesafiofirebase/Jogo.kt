package com.example.dhdesafiofirebase

import java.io.Serializable

data class Jogo(
    var titulo: String,
    var anoLancamento: Int,
    var descricao: String,
    var capa: String
) : Serializable
