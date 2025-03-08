package com.mercierlucas.psybudgetcompose.ui.custom

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ButtonCustom(
    label: String = "Confirm",
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
){
    Button(onClick = onClick, modifier = modifier) {
        Text(text = label)
    }
}

@Preview
@Composable
fun ButtonCustomPreview(){
    ButtonCustom("Validate",Modifier,{})
}