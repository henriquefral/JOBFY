package br.com.fiap.job_fy.search

import android.util.SparseArray
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.job_fy.R
import br.com.fiap.job_fy.components.InputText
import br.com.fiap.job_fy.model.Usuario
import br.com.fiap.job_fy.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController : NavController, usuario : Usuario) {

    var screenStatus by remember {
        mutableIntStateOf(0)
    }

    val usuarios = remember {
        mutableStateListOf<Usuario>()
    }

    val context  = LocalContext.current

    var idade by remember {
        mutableStateOf("0")
    }

    var situacao by remember {
        mutableIntStateOf(0)
    }

    var cargo by remember {
        mutableStateOf("")
    }

    var profissao by remember {
        mutableStateOf("")
    }

    var setor by remember {
        mutableStateOf("")
    }

    var anosExperiencia by remember {
        mutableStateOf("0")
    }

    var instituicao by remember {
        mutableStateOf("")
    }

    var graduacao by remember {
        mutableStateOf("")
    }

    var habilidade by remember {
        mutableStateOf("")
    }

    var descSituacao by remember {
        mutableStateOf("Desempregado")
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    Column(
        Modifier.padding(15.dp)) {
        Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Image(
                painter = painterResource(id = R.drawable.backicon),
                contentDescription = "back",
                modifier = Modifier
                    .size(120.dp, 120.dp)
                    .padding(bottom = 10.dp)
                    .clickable {
                        if ( usuarios.size > 0 && screenStatus == 1 )
                        {
                            screenStatus = 0
                        }
                        else
                        {

                            navController.popBackStack()

                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "id",
                                value = usuario.id
                            )

                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "nome",
                                value = usuario.nome
                            )

                            navController.navigate("menu")
                        }
                    }
            )
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                text = "Busca de usuários",
                fontSize = 30.sp,
                fontFamily = FontFamily(Font(R.font.marcellussc_regular))
            )
        }
        if ( usuarios.size > 0 && screenStatus == 1 )
        {
            LazyColumn (
                modifier = Modifier.fillMaxSize().padding(top = 15.dp)
            ) {
                items(usuarios) {
                    Text(text = "${it.nome} ${it.sobrenome}", fontSize = 30.sp)
                    Text(text = it.profissao, fontSize = 25.sp)
                    Text(text = "${it.getIdade()} anos" , fontSize = 25.sp)
                    Text(text = it.getSituacao(), fontSize = 25.sp)
                    Text(text = "${it.anos} anos de experiência", fontSize = 25.sp)
                    Text(text = it.descritivo, fontSize = 25.sp)
                    LazyColumn(modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(top = 15.dp))
                    {
                        items(it.habilidade) {
                            OutlinedCard( colors = CardDefaults.cardColors()
                                , modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 15.dp)
                            ) {
                                Column(modifier = Modifier
                                    .fillMaxSize()
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
                    LazyColumn(modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp))
                    {
                        items(it.formacao) {
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
                    Row(
                        modifier = Modifier
                            .padding(start = 15.dp, end = 30.dp, top = 15.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.like),
                            contentDescription = "like",
                            modifier = Modifier
                                .size(90.dp)
                                .padding(bottom = 10.dp)
                                .clickable {
                                    it.like(usuario.id, context)
                                    usuarios.remove(it)
                                }
                        )

                        Image(
                            painter = painterResource(id = R.drawable.dislike),
                            contentDescription = "dislike",
                            modifier = Modifier
                                .size(90.dp)
                                .padding(bottom = 10.dp)
                                .clickable {
                                    it.dislike(usuario.id, context)
                                    usuarios.remove(it)
                                }
                        )
                    }
                }
            }
        }
        else
        {
            Column (modifier = Modifier.verticalScroll(rememberScrollState()))
            {
                InputText(value = idade, valueOnChange = { idade = it }, text = "Idade: "
                        , keyboardType = KeyboardType.Number)
                Text(text = "Situação", fontSize = 30.sp, modifier = Modifier.padding(top = 15.dp))
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {

                    TextField(
                        value = descSituacao,
                        onValueChange = { descSituacao = it },
                        modifier = Modifier.menuAnchor(),
                        enabled = false,
                        readOnly = true,
                        colors = TextFieldDefaults.colors(
                            disabledTextColor = Color.Black, disabledIndicatorColor = Color.Black
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        DropdownMenuItem(
                            text = { Text("Desempregado") },
                            onClick = {
                                situacao = 1
                                descSituacao = "Desempregado"
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Empregado") },
                            onClick = {
                                situacao = 2
                                descSituacao = "Empregado"
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Aberto à negociações") },
                            onClick = {
                                situacao = 3
                                descSituacao = "Aberto à negociações"
                                expanded = false
                            }
                        )
                    }
                }
                InputText(value = cargo, valueOnChange = { cargo = it }, text = "Cargo: ")
                InputText(value = profissao, valueOnChange = { profissao = it }, text = "Profissão: ")
                InputText(value = setor, valueOnChange = { setor = it }, text = "Setor: ")
                InputText(
                    value = anosExperiencia,
                    valueOnChange = { anosExperiencia = it },
                    text = "Anos de experiência: ",
                    keyboardType = KeyboardType.Number)
                InputText(
                    value = instituicao,
                    valueOnChange = { instituicao = it },
                    text = "Instituição de formação: ")
                InputText(
                    value = graduacao,
                    valueOnChange = { graduacao = it },
                    text = "Nível de graduação: ")
                InputText(
                    value = habilidade,
                    valueOnChange = { habilidade = it },
                    text = "Habilidade: "
                )
                Button(onClick = {
                    val call = RetrofitFactory().getUsuarioService().pesquisaUsuarios(
                        usuario.id, idade.toInt(), situacao, cargo, profissao, setor, instituicao
                      , anosExperiencia.toInt(), graduacao, habilidade)


                    call.enqueue(object : Callback<List<Usuario>> {
                        override fun onResponse(
                            call: Call<List<Usuario>>,
                            response: Response<List<Usuario>>
                        ) {
                            screenStatus = 1
                            response.body()!!.forEach { usuarios.add(element = it) }
                        }

                        override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {

                            if (t.message!!.isNotEmpty()) {
                                Toast.makeText(context, t.message!!, Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    })
                }, modifier = Modifier.padding(top = 30.dp)) {

                    Text(
                        text = "Pesquisar!",
                        fontSize = 30.sp,
                        fontFamily = FontFamily(Font(R.font.marcellussc_regular))
                    )
                }
            }
        }
    }
}