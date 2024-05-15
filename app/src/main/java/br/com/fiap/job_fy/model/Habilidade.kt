package br.com.fiap.job_fy.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class Habilidade (
    var nome        : String = "",
    var descricao   : String = "",
) {

    var errorNome      by mutableStateOf(false)
    var errorDescricao by mutableStateOf(false)

    fun vldHabilidade() : Boolean {

        var vld = true

        if ( nome.isEmpty() )
        {
            errorNome = true
        } else
        {
            errorNome = false
        }

        if ( descricao.isEmpty() )
        {
            errorDescricao = true
        } else
        {
            errorDescricao = false
        }

        if ( errorNome || errorDescricao )
        {
            vld = false
        } else
        {
            vld = true
        }

        return vld;
    }

    fun limpa () {
        nome      = ""
        descricao = ""
    }
}