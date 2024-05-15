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
import br.com.fiap.job_fy.model.Habilidade
import br.com.fiap.job_fy.model.Usuario

@Composable
fun Step4 (usuario: Usuario) {

    var nome by remember {
        mutableStateOf("")
    }

    var descricao by remember {
        mutableStateOf("")
    }

    var mostraHabiliadades by remember {
        mutableStateOf(false)
    }

    var obsBtn by remember {
        mutableStateOf("Ver")
    }

    val habilidade = Habilidade()

    val listaHabilidade = remember {
        mutableStateListOf<Habilidade>()
    }

    LaunchedEffect(Unit) {
        usuario.habilidade.forEach {
            listaHabilidade.add(it)
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

                Text(text = "Habilidades!", fontSize = 35.sp)

                InputText(text = "Digite uma das várias"
                    ,error = habilidade.errorNome || usuario.errorHabilidade
                    ,value = nome
                    ,valueOnChange = { nome = it; habilidade.nome = it } )

                InputText(text = "Descreva sobre"
                    ,error = habilidade.errorDescricao || usuario.errorHabilidade
                    ,value = descricao
                    ,valueOnChange = { descricao = it; habilidade.descricao = it } )

                Row {
                    Button(onClick = { if ( habilidade.vldHabilidade() ) {
                        listaHabilidade.add(habilidade.copy())
                        usuario.habilidade = listaHabilidade
                        usuario.errorHabilidade = false
                        habilidade.limpa()
                        nome = ""
                        descricao = ""
                    }
                    }
                        ,modifier = Modifier.padding(top = 15.dp)) {
                        Text(text = "Salvar habilidade!", fontSize = 17.sp
                            ,fontFamily = FontFamily(Font(R.font.marcellussc_regular)))
                    }

                    Button(onClick = { if ( mostraHabiliadades )
                    {
                        mostraHabiliadades = false; obsBtn = "Ver"
                    } else {
                        mostraHabiliadades = true; obsBtn = "Esconder"
                    }
                    }
                        ,modifier = Modifier.padding(top = 15.dp, start = 42.dp)) {

                        Text(text = obsBtn,fontSize = 17.sp
                            ,fontFamily = FontFamily(Font(R.font.marcellussc_regular)))
                    }
                }
            }
        }
        if ( mostraHabiliadades && listaHabilidade.size > 0 ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(top = 15.dp)
            ) {
                items(listaHabilidade) {
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
                            Text(text = it.nome,fontSize = 19.sp
                                ,fontFamily = FontFamily(Font(R.font.marcellussc_regular)))
                            Spacer(modifier = Modifier.height(9.dp))
                            Text(text = it.descricao,fontSize = 16.sp
                                ,fontFamily = FontFamily(Font(R.font.marcellussc_regular)))

                            Button(onClick = {
                                listaHabilidade.remove(it)
                                usuario.habilidade = listaHabilidade
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