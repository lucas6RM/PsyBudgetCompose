package com.mercierlucas.psybudgetcompose.ui.custom

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ButtonCustom(
    onClick: () -> Unit,
    label : String = "Confirm"
){
    Button(onClick = onClick) {
        Text(text = label)
    }
}

@Preview
@Composable
fun ButtonCustomPreview(){
    ButtonCustom({},"Validate")
}