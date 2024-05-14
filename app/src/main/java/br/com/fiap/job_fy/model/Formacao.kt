package br.com.fiap.job_fy.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Formacao (
    var instituicao : String = "",
    var graduacao   : String = "",
    var descricao   : String = ""
) {

    var errorInstituicao  by mutableStateOf(false)
    var errorGraduacao    by mutableStateOf(false)
    var errorDescricao    by mutableStateOf(false)

    fun vldFormacao() : Boolean {

        var vld = true

        if ( instituicao.isEmpty() )
        {
            errorInstituicao = true
        } else
        {
            errorInstituicao = false
        }

        if ( graduacao.isEmpty() )
        {
            errorGraduacao = true
        } else
        {
            errorGraduacao = false
        }

        if ( descricao.isEmpty() )
        {
            errorDescricao = true
        } else
        {
            errorDescricao = false
        }

        if ( errorDescricao || errorGraduacao || errorInstituicao )
        {
            vld = false
        } else
        {
            vld = true
        }

        return vld;
    }

    fun limpa () {
        descricao = ""
        graduacao = ""
        instituicao = ""
    }
}