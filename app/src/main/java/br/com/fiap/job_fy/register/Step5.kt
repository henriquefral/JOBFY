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
fun Step5 (usuario: Usuario) {

    var descritivo by remember {
        mutableStateOf(usuario.descritivo)
    }

    Column {
        OutlinedCard( colors = CardDefaults.cardColors()
            , modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
        )
        {
            Column (modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 17.dp)) {

                Text(text = "Fale sobre você!", fontSize = 35.sp)

                Text(text = "Uma breve apresentação sobre quem você é!", fontSize = 20.sp)

                InputText(text = ""
                    ,error = usuario.errorDescritivo
                    ,value = descritivo
                    ,valueOnChange = { descritivo = it; usuario.descritivo = it } )

            }
        }
    }
}