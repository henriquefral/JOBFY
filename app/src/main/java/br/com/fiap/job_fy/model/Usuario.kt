package br.com.fiap.job_fy.model

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import br.com.fiap.job_fy.service.RetrofitFactory
import com.google.gson.annotations.Expose
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter

class Usuario (
    var nome               : String = "",
    var sobrenome          : String = "",
    var dataNascimento     : String = LocalDateTime.now().format(
                                                            DateTimeFormatter
                                                            .ofPattern("dd/MM/yyyy")
                                                           ),
    var situacao           : Int    = 0,
    var cargo              : String = "",
    var profissao          : String = "",
    var setor              : String = "",
    var anos               : Int    = 0,
    var formacao           : @RawValue List<Formacao>   = listOf(),
    var habilidade         : @RawValue List<Habilidade> = listOf(),
    var descritivo         : String = "",
    var loginId            : String = "",
    var loginPass          : String = "",
    var id                 : Int    = 1
) {
    
    @delegate:Expose(deserialize = false, serialize = false)
    var errorNome           by mutableStateOf(false)
    
    @delegate:Expose(deserialize = false, serialize = false)
    var errorSobrenome      by mutableStateOf(false)
    
    @delegate:Expose(deserialize = false, serialize = false)
    var errorDataNascimento by mutableStateOf(false)
    
    @delegate:Expose(deserialize = false, serialize = false)
    var errorCargo          by mutableStateOf(false)
    
    @delegate:Expose(deserialize = false, serialize = false)
    var errorProfissao      by mutableStateOf(false)
    
    @delegate:Expose(deserialize = false, serialize = false)
    var errorSetor          by mutableStateOf(false)

    
    @delegate:Expose(deserialize = false, serialize = false)
    var errorFormacao       by mutableStateOf(false)

    
    @delegate:Expose(deserialize = false, serialize = false)
    var errorHabilidade     by mutableStateOf(false)

    
    @delegate:Expose(deserialize = false, serialize = false)
    var errorDescritivo     by mutableStateOf(false)

    
    @delegate:Expose(deserialize = false, serialize = false)
    var errorLoginId        by mutableStateOf(false)

    
    @delegate:Expose(deserialize = false, serialize = false)
    var errorLoginPass      by mutableStateOf(false)

    
    @Expose(deserialize = false, serialize = false)
    var errorCadastro       : String = ""

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
                errorLoginId = loginId.isEmpty()

                errorLoginPass = loginPass.isEmpty()

                vld = !(errorLoginPass || errorLoginId)
            }
        }

        return vld
    }

    fun getIdade() : Int {
        val period = Period.between(LocalDate.parse(dataNascimento,
            DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalDate.now())

        return period.years;
    }

    fun getSituacao() : String {

        var result = ""

        when(situacao) {
            1 -> { result = "Desempregado"}
            2 -> { result = "Empregado"}
            3 -> { result = "Empregado"}
        }

        return result
    }

    fun login(context : Context, navController : NavController) {

        val call = RetrofitFactory().getUsuarioService().loginUsuario(loginId, loginPass)

        call.enqueue(object : Callback<Usuario> {
            override fun onResponse(
                call: Call<Usuario>,
                response: Response<Usuario>
            ) {
                Toast.makeText(context, "Usuário logado!", Toast.LENGTH_LONG)
                    .show()
                val usuario = response.body()!!

                id = usuario.id
                nome = usuario.nome
                sobrenome = usuario.sobrenome
                dataNascimento = usuario.dataNascimento
                situacao = usuario.situacao
                cargo = usuario.cargo
                profissao = usuario.profissao
                setor = usuario.setor
                anos = usuario.anos
                formacao = usuario.formacao
                habilidade = usuario.habilidade
                descritivo = usuario.descritivo

                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = "id",
                    value = id
                )

                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = "nome",
                    value = nome
                )

                navController.navigate("menu")
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {

                errorCadastro = "Não foi implementado!"

                if (t.message!!.isNotEmpty()) {
                    errorCadastro = t.message!!
                }

                Toast.makeText(context, errorCadastro, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    fun cadastro(context : Context, navController: NavController) {

        val call = RetrofitFactory().getUsuarioService().cadastroUsuario(this)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Toast.makeText(context, "Usuário salvo!", Toast.LENGTH_LONG)
                    .show()

                navController.navigate("login")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                errorCadastro = "Não foi implementado!"

                if (t.message!!.isNotEmpty()) {
                    errorCadastro = t.message!!
                }

                Toast.makeText(context, errorCadastro, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    fun like(id: Int, context : Context) {

        val call = RetrofitFactory().getUsuarioService().like(id, this.id)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Toast.makeText(context, "Match!!", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                errorCadastro = "Não foi implementado!"

                if (t.message!!.isNotEmpty()) {
                    errorCadastro = t.message!!
                }

                Toast.makeText(context, errorCadastro, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    fun dislike(id: Int, context : Context) {
        val call = RetrofitFactory().getUsuarioService().dislike(id, this.id)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Toast.makeText(context, "Match!!", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                errorCadastro = "Não foi implementado!"

                if (t.message!!.isNotEmpty()) {
                    errorCadastro = t.message!!
                }

                Toast.makeText(context, errorCadastro, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
}