package br.com.fiap.job_fy.service
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    private val URL = "https://664411086c6a656587091cca.mockapi.io/api/v1/"

    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getUsuarioService(): UsuarioService {
        return retrofitFactory.create(UsuarioService::class.java)
    }

}