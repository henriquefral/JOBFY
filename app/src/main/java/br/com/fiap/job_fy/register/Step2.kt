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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.job_fy.components.InputText
import br.com.fiap.job_fy.model.Usuario

@Composable
fun Step2 (usuario: Usuario) {

    var cargo by remember {
        mutableStateOf(usuario.cargo)
    }

    var profissao by remember {
        mutableStateOf(usuario.profissao)
    }

    var setor by remember {
        mutableStateOf(usuario.setor)
    }

    var anos by remember {
        mutableStateOf(usuario.anos.toString())
    }

    val obsDados = if (usuario.situacao == 1) "desejados" else "atuais"

    val obsAnos = if (usuario.situacao == 1) "experiência" else "duração"

    Column {
        OutlinedCard( colors = CardDefaults.cardColors()
                    , modifier = Modifier.fillMaxWidth().padding(top = 15.dp) )
        {
            Column (modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 17.dp)) {

                Text(text = "Dados ".plus(obsDados), fontSize = 35.sp)

                InputText(text = "Profissão", error = usuario.errorProfissao, value = profissao
                         ,valueOnChange = { profissao = it; usuario.profissao = it } )

                InputText(text = "Cargo", error = usuario.errorCargo, value = cargo
                         ,valueOnChange = { cargo = it; usuario.cargo = it } )

                InputText(text = "Setor", error = usuario.errorSetor, value = setor
                         ,valueOnChange = { setor = it; usuario.setor = it } )

                InputText(text = "Anos de ".plus(obsAnos), value = anos
                         ,valueOnChange = { anos = it; if ( it.isNotEmpty() ) { usuario.anos = it.toInt() } }
                         ,keyboardType = KeyboardType.Number )

            }
        }
    }
}