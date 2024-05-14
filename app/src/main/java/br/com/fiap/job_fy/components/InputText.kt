package br.com.fiap.job_fy.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputText (value : String, error : Boolean = false, textModifier: Modifier = Modifier
               , modifierTextField: Modifier = Modifier
               , valueOnChange: (String) -> Unit, text: String
               , keyboardType : KeyboardType = KeyboardType.Text) {

    Text(text = text, fontSize = 30.sp
        , modifier = textModifier.padding(top = 15.dp))
    TextField(value = value, onValueChange = valueOnChange
        , isError = error, modifier = modifierTextField
        , keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}