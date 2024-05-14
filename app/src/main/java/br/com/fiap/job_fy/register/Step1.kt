package br.com.fiap.job_fy.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.job_fy.components.InputText
import br.com.fiap.job_fy.model.Usuario
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Step1 (usuario: Usuario) {

    var showDatePickerDialog by remember {
        mutableStateOf(false)
    }
    val datePickerState = rememberDatePickerState()
    val focusManager = LocalFocusManager.current

    var selectedDate by remember {
        mutableStateOf(usuario.dataNascimento)
    }
    var nome by remember {
        mutableStateOf(usuario.nome)
    }
    var sobrenome by remember {
        mutableStateOf(usuario.sobrenome)
    }
    var expanded by remember {
        mutableStateOf(false)
    }
    var descSituacao by remember {
        mutableStateOf("Desempregado")
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
                            usuario.dataNascimento = formatter.format(millis)
                        }
                        showDatePickerDialog = false
                    }
                ) {
                    Text(text = "Escolher data")
                }
            }
        )
        {
            DatePicker(state = datePickerState
                     , dateFormatter = DatePickerFormatter("MM/dd/yyyy"
                                                          ,"MM/dd/yyyy"
                                                          ,"MM/dd/yyyy")
            )
        }
    }

    Column {
        OutlinedCard( colors = CardDefaults.cardColors()
                    , modifier = Modifier.fillMaxWidth().padding(top = 15.dp) )
        {
            Column (modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 17.dp)) {

                InputText(text = "Nome: ", error = usuario.errorNome, value = nome
                    , valueOnChange = { nome = it; usuario.nome = it } )

                InputText(text = "Sobrenome: ", error = usuario.errorSobrenome, value = sobrenome
                         , valueOnChange = { sobrenome = it; usuario.sobrenome = it} )

                InputText(text = "Data de nascimento: ", error = usuario.errorDataNascimento
                        , valueOnChange = {}, value = selectedDate
                        , modifierTextField = Modifier.onFocusEvent {
                                                        if (it.isFocused) {
                                                            showDatePickerDialog = true
                                                            focusManager.clearFocus(force = true)
                                                        }
                                                    }
                        )

                Text(text="Situação", fontSize = 30.sp, modifier=Modifier.padding(top = 15.dp) )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {

                    TextField(value=descSituacao, onValueChange = { descSituacao = it }
                             ,modifier = Modifier.menuAnchor(), enabled = false, readOnly = true
                             , colors = TextFieldDefaults.colors(
                                                             disabledTextColor = Color.Black
                                                           , disabledIndicatorColor = Color.Black)
                             )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        DropdownMenuItem(
                            text = { Text("Desempregado") },
                            onClick = {
                                usuario.situacao = 1
                                descSituacao = "Desempregado"
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Empregado") },
                            onClick = {
                                usuario.situacao = 2
                                descSituacao = "Empregado"
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Aberto à negociações") },
                            onClick = {
                                usuario.situacao = 3
                                descSituacao = "Aberto à negociações"
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}