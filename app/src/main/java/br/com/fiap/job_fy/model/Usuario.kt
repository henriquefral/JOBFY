package br.com.fiap.job_fy.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import br.com.fiap.job_fy.service.RetrofitFactory
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    var formacao           : List<Formacao>   = listOf(),
    var habilidade         : List<Habilidade> = listOf(),
    var descritivo         : String = "",
    var email              : String = "",
    var senha              : String = ""
) {
    var errorNome           by mutableStateOf(false)
    var errorSobrenome      by mutableStateOf(false)
    var errorDataNascimento by mutableStateOf(false)

    var errorCargo          by mutableStateOf(false)
    var errorProfissao      by mutableStateOf(false)
    var errorSetor          by mutableStateOf(false)


    var errorFormacao       by mutableStateOf(false)

    var errorHabilidade     by mutableStateOf(false)

    var errorDescritivo     by mutableStateOf(false)

    var errorEmail          by mutableStateOf(false)
    var errorSenha          by mutableStateOf(false)

    var errorCadastro : String = ""

    fun vldPage(page : Int) : Boolean
    {
        var vld = true

        when (page) {
            0 -> {
                errorNome = this.nome.isEmpty()

                errorSobrenome = this.sobrenome.isEmpty()

                errorDataNascimento = if ( this.dataNascimento.isEmpty() ) {
                    true
                } else {
                    val period = Period.between(LocalDate.parse(dataNascimento,
                        DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalDate.now())
                    period.years < 16
                }

                vld = !(errorDataNascimento || errorNome || errorSobrenome)

            }
            1 -> {

                errorCargo = cargo.isEmpty()

                errorSetor = setor.isEmpty()

                errorProfissao = profissao.isEmpty()

                vld = !(errorCargo || errorSetor || errorProfissao)

            }
            2 -> {

                errorFormacao = formacao.isEmpty()

                vld = !errorFormacao

            }
            3 -> {

                errorHabilidade = habilidade.isEmpty()

                vld = !errorHabilidade
            }
            4 -> {
                errorDescritivo = descritivo.isEmpty()

                vld = !errorDescritivo
            }
            5 -> {
                errorEmail = email.isEmpty()

                errorSenha = senha.isEmpty()

                vld = !(errorEmail || errorSenha)
            }
        }

        return vld
    }

    fun cadastro() : Boolean {

        val call = RetrofitFactory().getUsuarioService().cadastroUsuario(this)
        var vld  = false

        call.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                vld = true
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                errorCadastro = "Não foi implementado!"

                if ( t.message!!.isNotEmpty() ) {
                    errorCadastro = t.message!!
                }

                vld = false
            }
        })

        return vld;
    }
}