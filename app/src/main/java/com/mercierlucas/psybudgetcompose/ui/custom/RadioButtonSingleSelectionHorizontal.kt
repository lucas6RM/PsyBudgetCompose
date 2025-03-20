package com.mercierlucas.psybudgetcompose.ui.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RadioButtonSingleSelectionHorizontal(
    modifier: Modifier = Modifier,
    radioOptions : List<String> = listOf("1", "2", "3"),
    callbackRBSelected:(String) ->  Unit,
    enabled: Boolean = true
) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    Row(modifier.selectableGroup()) {
        radioOptions.forEach { text ->
            Column(
                Modifier
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            callbackRBSelected.invoke(text)
                                  },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null,
                    enabled = enabled
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun RadioButtonSingleSelectionPreview() {
    RadioButtonSingleSelectionHorizontal(callbackRBSelected = {})
}