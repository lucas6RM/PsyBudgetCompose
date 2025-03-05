package com.mercierlucas.psybudgetcompose.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderCustom(title: String) {
    Column {
        Text(text = title,
            fontSize = 25.sp,
            modifier = Modifier
                .padding(20.dp)
        )
        Row (
            modifier = Modifier
                .background(Color.Black)
                .fillMaxWidth()
                .padding(3.dp)
        ) {}
    }
}

