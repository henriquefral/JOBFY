package br.com.fiap.job_fy.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter

class Usuario (
    var sobrenome          : String = "",
    var nome               : String = "",
    var dataNascimento     : String = LocalDateTime.now().format(
                                                            DateTimeFormatter
                                                            .ofPattern("dd/MM/yyyy")
                                                           ),
    var situacao           : Int    = 0,
    var cargo              : String = "",
    var profissao          : String = "",
    var setor              : String = "",
    var anos               : Int    = 0,
    var formacao           : List<Formacao> = listOf<Formacao>(),
    var habilidades        : List<String> = listOf<String>(""),
    var descritivo         : String = ""
) {
    var errorNome           by mutableStateOf(false)
    var errorSobrenome      by mutableStateOf(false)
    var errorDataNascimento by mutableStateOf(false)

    var errorCargo          by mutableStateOf(false)
    var errorProfissao      by mutableStateOf(false)
    var errorSetor          by mutableStateOf(false)


    var errorFormacao            by mutableStateOf(false)

    var errorHabilidades         by mutableStateOf(false)

    var errorDescritivo          by mutableStateOf(false)

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
                val period = Period.between(LocalDate.parse(dataNascimento,
                                                            DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                          , LocalDate.now())
                if ( period.years >= 16 )
                {
                    errorDataNascimento = false
                } else {
                    errorDataNascimento = true
                }
            }

            if ( errorDataNascimento || errorNome || errorSobrenome )
            {
                vld = false
            } else
            {
                vld = true
            }
        } else if ( page == 1 ) {

            if ( cargo.isEmpty() )
            {
                errorCargo = true
            } else {
                errorCargo = false
            }

            if ( setor.isEmpty() )
            {
                errorSetor = true
            } else
            {
                errorSetor = false
            }

            if ( profissao.isEmpty() )
            {
                errorProfissao = true
            } else
            {
                errorProfissao = false
            }

            if ( errorCargo || errorSetor || errorProfissao )
            {
                vld = false
            } else
            {
                vld = true
            }
        }

        return vld
    }
}