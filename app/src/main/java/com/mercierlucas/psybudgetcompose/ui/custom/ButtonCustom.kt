package com.mercierlucas.psybudgetcompose.ui.custom

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ButtonCustom(
    text: String = "Confirm",
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors()
){
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = colors,
        contentPadding = ButtonDefaults.TextButtonContentPadding
    )
    {
        Text(text = text,
            textAlign = TextAlign.Center)
    }
}

@Preview
@Composable
fun ButtonCustomPreview(){

    ButtonCustom("Validate",Modifier,{})
}