package br.com.fiap.job_fy.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.job_fy.R
import br.com.fiap.job_fy.components.InputText
import br.com.fiap.job_fy.model.Formacao
import br.com.fiap.job_fy.model.Usuario

@Composable
fun Step3 (usuario: Usuario) {

    var instituicao by remember {
        mutableStateOf("")
    }

    var graduacao by remember {
        mutableStateOf("")
    }

    var descricao by remember {
        mutableStateOf("")
    }

    var mostraFormacoes by remember {
        mutableStateOf(false)
    }

    var obsBtn by remember {
        mutableStateOf("Ver")
    }

    val formacao = Formacao()

    val listaFormacao = remember {
        mutableStateListOf<Formacao>()
    }

    LaunchedEffect(Unit) {
        usuario.formacao.forEach {
            listaFormacao.add(it)
        }
    }

    Column {
        OutlinedCard( colors = CardDefaults.cardColors()
                    , modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
        )
        {
            Column (modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 17.dp)) {

                Text(text = "Estudos!", fontSize = 35.sp)

                InputText(text = "Instituição"
                         ,error = formacao.errorInstituicao || usuario.errorFormacao
                         ,value = instituicao
                         ,valueOnChange = { instituicao = it; formacao.instituicao = it } )

                InputText(text = "Graduação"
                         ,error = formacao.errorGraduacao || usuario.errorFormacao
                         ,value = graduacao
                         ,valueOnChange = { graduacao = it; formacao.graduacao = it } )

                InputText(text = "Descreva"
                         ,error = formacao.errorDescricao || usuario.errorFormacao
                         ,value = descricao
                         ,valueOnChange = { descricao = it; formacao.descricao = it } )

                Row {
                    Button(onClick = { if ( formacao.vldFormacao() ) {
                                            listaFormacao.add(formacao.copy())
                                            usuario.formacao = listaFormacao
                                            usuario.errorFormacao = false
                                            formacao.limpa()
                                            instituicao = ""
                                            graduacao = ""
                                            descricao = ""
                                       }
                                     }
                          ,modifier = Modifier.padding(top = 15.dp)) {
                        Text(text = "Salvar estudo!", fontSize = 17.sp
                            ,fontFamily = FontFamily(Font(R.font.marcellussc_regular)))
                    }

                    Button(onClick = { if ( mostraFormacoes )
                                       {
                                            mostraFormacoes = false; obsBtn = "Ver"
                                       } else {
                                            mostraFormacoes = true; obsBtn = "Esconder"
                                       }
                                     }
                          ,modifier = Modifier.padding(top = 15.dp, start = 60.dp)) {

                        Text(text = obsBtn,fontSize = 17.sp
                            ,fontFamily = FontFamily(Font(R.font.marcellussc_regular)))
                    }
                }
            }
        }
        if ( mostraFormacoes && listaFormacao.size > 0 ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .height(210.dp)
                    .padding(top = 15.dp)
            ) {
                items(listaFormacao) {
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

                            Button(onClick = {
                                listaFormacao.remove(it)
                                usuario.formacao = listaFormacao
                            },modifier = Modifier.padding(top = 10.dp, end = 12.dp)
                                                 .align(Alignment.End)) {

                                Text(text = "Apagar",fontSize = 17.sp
                                    ,fontFamily = FontFamily(Font(R.font.marcellussc_regular)))
                            }
                        }
                    }
                }
            }
        }
    }
}