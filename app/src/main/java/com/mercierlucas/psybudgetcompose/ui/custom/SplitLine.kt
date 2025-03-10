package com.mercierlucas.psybudgetcompose.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SplitLine(){
    Row (
        modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth()
            .padding(1.dp)
    ) {}
}