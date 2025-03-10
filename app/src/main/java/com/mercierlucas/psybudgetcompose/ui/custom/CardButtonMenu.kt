package com.mercierlucas.psybudgetcompose.ui.custom

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardButtonMenu(
    text:String = "CardButtonMenu",
    enableClick: Boolean = true,
    onClick: () -> Unit = {}){
    Card (
        modifier = Modifier
            .padding(5.dp)
        ,
        border = BorderStroke(1.dp, Color.Black),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ){
        Column (modifier = Modifier
            .clickable(
                onClick = onClick,
                enabled = enableClick
            )){
            Text(
                text = text,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(Alignment.CenterVertically)
                ,
                textAlign = TextAlign.Center
            )
        }

    }
}

@Preview
@Composable
fun CardButtonMenuPreview(){
    CardButtonMenu()
}