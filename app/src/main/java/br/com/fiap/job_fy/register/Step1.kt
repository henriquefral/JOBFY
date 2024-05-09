package br.com.fiap.job_fy.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.job_fy.model.User
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Step1 (user: User) {

    var showDatePickerDialog by remember {
        mutableStateOf(false)
    }
    val datePickerState = rememberDatePickerState()
    val focusManager = LocalFocusManager.current

    var selectedDate by remember {
        mutableStateOf(user.dataNascimento)
    }
    var nome by remember {
        mutableStateOf(user.nome)
    }
    var sobrenome by remember {
        mutableStateOf(user.sobrenome)
    }

    if ( showDatePickerDialog ) {
        DatePickerDialog(
            onDismissRequest = { showDatePickerDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val formatter = SimpleDateFormat("dd/MM/yyyy",
                                                             Locale("pt-br"))
                                .apply { timeZone = TimeZone.getTimeZone("GMT") }
                            selectedDate = formatter.format(millis)
                            user.dataNascimento = formatter.format(millis)
                        }
                        showDatePickerDialog = false
                    }
                ) {
                    Text(text = "Escolher data")
                }
            }
        )
        {
            DatePicker(state = datePickerState)
        }
    }

    Column {
        OutlinedCard( colors = CardDefaults.cardColors()
            , modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp) )
        {
            Column (modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 17.dp)) {

                Text(text = "Nome: ", fontSize = 30.sp)
                TextField(value = nome, onValueChange = { nome = it; user.nome = it }
                        , isError = user.errorNome)

                Text(text = "Sobrenome: ", fontSize = 30.sp
                   , modifier = Modifier.padding(top = 15.dp))
                TextField(value = sobrenome, onValueChange = { sobrenome = it; user.sobrenome = it}
                        , isError = user.errorSobrenome)

                Text(text = "Data de nascimento: ", fontSize = 30.sp
                    , modifier = Modifier.padding(top = 15.dp))

                TextField(value = selectedDate,onValueChange = {}, readOnly = true
                    , modifier = Modifier.onFocusEvent {
                        if (it.isFocused) {
                            showDatePickerDialog = true
                            focusManager.clearFocus(force = true)
                        }
                    }, isError = user.errorDataNascimento
                )
            }
        }
    }
}