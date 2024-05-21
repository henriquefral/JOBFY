package br.com.fiap.job_fy.service

import br.com.fiap.job_fy.model.Usuario
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UsuarioService {

    @GET("login")
    fun loginUsuario(@Query("id") id: String, @Query("pass") pass: String): Call<Usuario>

    @GET("usuarios")
    fun getUsuarios(@Query("idUser") id: String): Call<List<Usuario>>

    @POST("cadastro")
    fun cadastroUsuario(@Body body: Usuario): Call<ResponseBody>

    @POST("like")
    fun like(@Query("id") id: Int, @Body body: Int): Call<ResponseBody>

    @POST("dislike")
    fun dislike(@Query("id") id: Int, @Body body: Int): Call<ResponseBody>

}