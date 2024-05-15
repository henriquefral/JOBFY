package br.com.fiap.job_fy.service

import br.com.fiap.job_fy.model.Usuario
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UsuarioService {

    @POST("login")
    fun loginUsuario(@Body body: Usuario): Call<ResponseBody>


    @POST("cadastro")
    fun cadastroUsuario(@Body body: Usuario): Call<ResponseBody>


}