package br.com.fiap.job_fy.menu

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.job_fy.R
import br.com.fiap.job_fy.model.Usuario
import br.com.fiap.job_fy.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MenuScreen (navController : NavController, usuario: Usuario) {

    var countUsuario = remember {
        mutableIntStateOf(0)
    }

    val usuarios = remember {
        mutableStateListOf<Usuario>()
    }

    val context  = LocalContext.current

    LaunchedEffect(Unit) {
        val call = RetrofitFactory().getUsuarioService().getUsuarios(usuario.id.toString())

        call.enqueue(object : Callback<List<Usuario>> {
            override fun onResponse(
                call: Call<List<Usuario>>,
                response: Response<List<Usuario>>
            ) {

                response.body()!!.forEach { usuarios.add(element = it) }
            }

            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {

                if (t.message!!.isNotEmpty()) {
                    Toast.makeText(context, t.message!!, Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }

    Column(modifier = Modifier.padding(start = 15.dp, top = 10.dp, end = 15.dp)) {
        Text(text = "Bem vindo,", fontSize = 40.sp)
        Text(text = "${usuario.nome}!", fontSize = 30.sp)

        if ( usuarios.size > 0 && countUsuario.intValue < usuarios.size && countUsuario.intValue >= 0 )
        {
            Column(modifier = Modifier.padding(top = 20.dp)) {
                Text(text = "${usuarios[countUsuario.intValue].nome} ${usuarios[countUsuario.intValue].sobrenome}", fontSize = 30.sp)
                Text(text = usuarios[countUsuario.intValue].profissao, fontSize = 25.sp)
                Text(text = "${usuarios[countUsuario.intValue].getIdade()} anos" , fontSize = 25.sp)
                Text(text = usuarios[countUsuario.intValue].getSituacao(), fontSize = 25.sp)
                Text(text = "${usuarios[countUsuario.intValue].anos} anos de experiência", fontSize = 25.sp)
                Text(text = usuarios[countUsuario.intValue].descritivo, fontSize = 25.sp)
                LazyColumn(modifier = Modifier.fillMaxWidth().height(120.dp).padding(top = 15.dp))
                {
                    items(usuarios[countUsuario.intValue].habilidade) {
                        OutlinedCard( colors = CardDefaults.cardColors()
                                    , modifier = Modifier.fillMaxWidth().padding(top = 15.dp)
                        ) {
                            Column(modifier = Modifier.fillMaxSize()
                                    .padding(start = 10.dp, top = 10.dp, bottom = 17.dp)
                            ) {
                                Text(text = it.nome,fontSize = 19.sp
                                    ,fontFamily = FontFamily(Font(R.font.marcellussc_regular))
                                )
                                Spacer(modifier = Modifier.height(9.dp))
                                Text(text = it.descricao,fontSize = 16.sp
                                    ,fontFamily = FontFamily(Font(R.font.marcellussc_regular))
                                )

                            }
                        }
                    }
                }
                LazyColumn(modifier = Modifier.fillMaxWidth().height(140.dp))
                {
                    items(usuarios[countUsuario.intValue].formacao) {
                        OutlinedCard( colors = CardDefaults.cardColors()
                            , modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 15.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 10.dp, top = 10.dp, bottom = 17.dp)
                            ) {
                                Text(text = it.instituicao,fontSize = 19.sp
                                    ,fontFamily = FontFamily(Font(R.font.marcellussc_regular)))
                                Spacer(modifier = Modifier.height(7.dp))
                                Text(text = it.graduacao,fontSize = 17.sp
                                    ,fontFamily = FontFamily(Font(R.font.marcellussc_regular)))
                                Spacer(modifier = Modifier.height(9.dp))
                                Text(text = it.descricao,fontSize = 16.sp
                                    ,fontFamily = FontFamily(Font(R.font.marcellussc_regular)))
                            }
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .padding(start = 15.dp, end = 30.dp, top = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Image(
                painter = painterResource(id = R.drawable.like),
                contentDescription = "back",
                modifier = Modifier
                    .size(120.dp, 120.dp)
                    .padding(bottom = 10.dp)
                    .clickable {
                        countUsuario.intValue++
                        usuarios[countUsuario.intValue].like(usuario.id)
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.dislike),
                contentDescription = "wallet",
                modifier = Modifier
                    .size(120.dp, 120.dp)
                    .padding(bottom = 10.dp)
                    .clickable {
                        countUsuario.intValue++
                        usuarios[countUsuario.intValue].dislike(usuario.id)
                    }
            )
        }
    }
}