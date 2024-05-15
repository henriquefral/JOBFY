package br.com.fiap.job_fy.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.job_fy.components.InputText
import br.com.fiap.job_fy.model.Usuario

@Composable
fun Step6 (usuario: Usuario) {

    var email by remember {
        mutableStateOf(usuario.email)
    }

    var senha by remember {
        mutableStateOf(usuario.senha)
    }

    var senhaConfirmada by remember {
        mutableStateOf(usuario.senha)
    }

    Column {
        OutlinedCard( colors = CardDefaults.cardColors()
            , modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
        )
        {
            Column (modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 17.dp)) {

                Text(text = "Login", fontSize = 35.sp)

                InputText(text = "E-mail"
                    ,error = usuario.errorEmail
                    ,value = email
                    ,valueOnChange = { email = it; usuario.email = it } )

                InputText(text = "Senha"
                    ,error = usuario.errorSenha
                    ,value = senha
                    ,valueOnChange = { senha = it } )


                InputText(text = "Confirmar a senha"
                    ,error = senha != senhaConfirmada
                    ,value = senhaConfirmada
                    ,valueOnChange = { senhaConfirmada = it
                    if ( senhaConfirmada == senha ) { usuario.senha = senhaConfirmada; }
                    } )

            }
        }
    }
}