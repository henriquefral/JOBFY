package br.com.fiap.job_fy.login_

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.job_fy.R
import br.com.fiap.job_fy.model.Usuario

@Composable
fun LoginScreen(navController: NavController) {

    var loginId   by remember { mutableStateOf("") }
    var loginPass by remember { mutableStateOf("") }

    var errorId by remember { mutableStateOf(false) }
    var errorPass  by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        var usuario = Usuario()
        usuario.login(context = context, navController = navController)
    }

    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(top = 30.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.wallet),
            contentDescription = "wallet",
            modifier = Modifier
                .size(120.dp, 120.dp)
                .padding(bottom = 10.dp)
        )
        Text(text = "Jobfy!", fontSize = 35.sp)
        Box(modifier = Modifier.padding(top = 20.dp)) {
            Column (modifier = Modifier.width(330.dp)) {
                Text(text = "Identificação:", fontSize = 30.sp, modifier = Modifier.padding(bottom = 10.dp))
                TextField(value = loginId, onValueChange = {loginId = it}, isError = errorId
                         ,modifier = Modifier.width(330.dp))
                Text(text = "Senha:", fontSize = 30.sp, modifier = Modifier.padding(bottom = 10.dp, top = 20.dp))
                TextField(value = loginPass, onValueChange = {loginPass = it}, isError = errorPass
                         ,modifier = Modifier.width(330.dp) )
                Text(text = "Esqueci a senha!", fontSize = 18.sp, modifier = Modifier.padding(top = 3.dp), fontStyle = FontStyle.Italic)
                Text(text = "Cadastre-se!", fontSize = 18.sp, modifier = Modifier.padding(top = 7.dp).clickable { navController.navigate("register") }, textDecoration = TextDecoration.Underline)

                Button(onClick = {errorId = loginId.isEmpty(); errorPass = loginPass.isEmpty()
                                 if ( !errorId && !errorPass ) {
                                    var usuario = Usuario()

                                    usuario.loginId = loginId
                                    usuario.loginPass = loginPass

                                    usuario.login(context, navController)
                                 } }
                      ,modifier = Modifier.padding(top = 30.dp)) {

                    Text(text = "Entrar!",fontSize = 30.sp
                        ,fontFamily = FontFamily(Font(R.font.marcellussc_regular))
                    )
                }
            }
        }
    }
}