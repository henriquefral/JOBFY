package br.com.fiap.job_fy.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class User (
    var sobrenome          : String = "",
    var nome               : String = "",
    var dataNascimento     : String = LocalDateTime.now().format(
                                                            DateTimeFormatter
                                                            .ofPattern("dd/MM/yyyy")
                                                           ),
) {
    var errorNome           by mutableStateOf(false)
    var errorSobrenome      by mutableStateOf(false)
    var errorDataNascimento by mutableStateOf(false)

    fun VldPage(page : Int) : Boolean
    {
        var vld = true

        if ( page == 0 )
        {
            if ( this.nome.isEmpty() )
            {
                errorNome = true
            } else {
                errorNome = false
            }

            if ( this.sobrenome.isEmpty() )
            {
                errorSobrenome = true
            } else {
                errorSobrenome = false
            }

            if ( this.dataNascimento.isEmpty() )
            {
                errorDataNascimento = true
            } else {
                errorDataNascimento = false
            }

            if ( !errorDataNascimento && !errorNome && !errorSobrenome ) {
                vld = true
            } else {
                vld = false
            }
        }

        return vld;
    }
}