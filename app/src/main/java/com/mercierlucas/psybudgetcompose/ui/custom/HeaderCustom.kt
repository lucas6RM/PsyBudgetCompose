package com.mercierlucas.psybudgetcompose.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderCustom(title: String = "Header") {
    Column {
        Text(
            text = title,
            modifier = Modifier
                .padding(10.dp),
            style = MaterialTheme.typography.titleLarge
        )
        SplitLine()
    }
}


@Preview
@Composable
fun HeaderCustomPreview(){
    HeaderCustom()
}