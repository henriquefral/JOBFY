package br.com.fiap.job_fy.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.job_fy.R
import br.com.fiap.job_fy.model.Usuario

@Composable
fun RegisterScreen(navController: NavController) {

    var usuario by remember { mutableStateOf(Usuario()) }
    var page by remember { mutableIntStateOf(2) }

    Column (modifier = Modifier
        .padding(top = 15.dp, start = 10.dp, end = 10.dp)
        .fillMaxWidth()
        .verticalScroll(
            rememberScrollState()
        ))
    {

        Text(text = "Informe seus dados!", fontSize = 40.sp)

        if ( page == 0 ) {
            Step1(usuario = usuario)
        } else if ( page == 1 ) {
            Step2(usuario = usuario)
        } else if ( page == 2 ) {
            Step3(usuario = usuario)
        }

        Row (modifier = Modifier
            .padding(start = 15.dp, end = 15.dp, top = 10.dp)
            .fillMaxWidth(),
             horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Image(
                painter = painterResource(id = R.drawable.backicon),
                contentDescription = "back",
                modifier = Modifier
                    .size(120.dp, 120.dp)
                    .padding(bottom = 10.dp)
                    .clickable {
                        if (page > 0) {
                            page--
                        }
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.nexticon),
                contentDescription = "wallet",
                modifier = Modifier
                    .size(120.dp, 120.dp)
                    .padding(bottom = 10.dp)
                    .clickable {
                        if (usuario.VldPage(page)) {
                            page++
                        }
                    }
            )
        }
    }
}