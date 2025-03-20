package com.mercierlucas.psybudgetcompose.ui.patients.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mercierlucas.feedarticles.Utils.showToast
import com.mercierlucas.psybudgetcompose.R
import com.mercierlucas.psybudgetcompose.data.network.dtos.CreatePatientDto
import com.mercierlucas.psybudgetcompose.ui.custom.ButtonCustom
import com.mercierlucas.psybudgetcompose.ui.custom.HeaderCustom
import com.mercierlucas.psybudgetcompose.ui.custom.OutlinedTextFieldCustom
import com.mercierlucas.psybudgetcompose.ui.custom.RadioButtonSingleSelectionHorizontal
import com.mercierlucas.psybudgetcompose.utils.theme.PsyBudgetComposeTheme
import java.util.Locale


@Composable
fun CreatePatientScreen(
    navController: NavHostController,
    createPatientViewModel: CreatePatientViewModel,
) {
    val isProgressBarActive by createPatientViewModel.isProgressBarDisplayedStateFlow
        .collectAsState()

    val context = LocalContext.current

    with(createPatientViewModel){
        CreatePatientView(isProgressBarActive) { newPatient ->
            validateInputs(newPatient)
        }

        LaunchedEffect(true) {
            messageSharedFlow.collect { message ->
                context.showToast(message)
            }
        }

        LaunchedEffect(true) {
            goToAllPatientScreenSharedFlow.collect { yes ->
                if(yes)
                    navController.popBackStack()
            }
        }
    }

}

@Composable
fun CreatePatientView(isProgressBarActive: Boolean, onClickConfirmButton: (CreatePatientDto) -> Unit ){

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf<String?>(null) }
    var numberSS by remember { mutableStateOf<Int?>(null) }
    var address by remember { mutableStateOf<String?>(null) }
    var postalCode by remember { mutableStateOf<Int?>(null) }
    var city by remember { mutableStateOf<String?>(null) }
    var age by remember { mutableStateOf<Int?>(null) }
    var income by remember { mutableStateOf("Low") }

    var isActive by remember {
        mutableStateOf(false)
    }

    var isRequiringInvoice by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HeaderCustom(stringResource(id = R.string.create_a_new_patient))

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 50.dp)
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextFieldCustom(
                value = firstName,
                onValueChange = { firstName = it },
                labelText = stringResource(id = R.string.first_name_required)
            )
            OutlinedTextFieldCustom(
                value = lastName,
                onValueChange = { lastName = it },
                labelText = stringResource(id = R.string.last_name_required)
            )
            OutlinedTextFieldCustom(
                value = phone,
                onValueChange = { phone = it },
                labelText = stringResource(id = R.string.phone_number_required),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            OutlinedTextFieldCustom(
                value = email ?: "",
                onValueChange = { email = it },
                labelText = stringResource(id = R.string.email),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )


            OutlinedTextFieldCustom(
                value = numberSS?.toString() ?: "" ,
                onValueChange = { numberSS = it.toInt() },
                labelText = stringResource(id = R.string.number_ss),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextFieldCustom(
                value = address ?: "",
                onValueChange = { address = it },
                labelText = stringResource(id = R.string.address)
            )

            OutlinedTextFieldCustom(
                value = postalCode?.toString() ?: "",
                onValueChange = { postalCode = it.toInt() },
                labelText = stringResource(id = R.string.postal_code),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextFieldCustom(
                value = city ?: "",
                onValueChange = { city = it },
                labelText = stringResource(id = R.string.city)
            )
            OutlinedTextFieldCustom(
                value = age?.toString() ?: "",
                onValueChange = { age = it.toInt() },
                labelText = stringResource(id = R.string.age),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Row(
                Modifier.padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.income),
                    style = MaterialTheme.typography.bodyMedium
                )
                RadioButtonSingleSelectionHorizontal(
                    radioOptions = listOf(
                        stringResource(id = R.string.low),
                        stringResource(id = R.string.mid),
                        stringResource(id = R.string.high)
                    ),
                    callbackRBSelected = { radioButtonSelected ->
                        income = radioButtonSelected.lowercase()
                    })
            }

            Row(
                Modifier.padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.is_in_therapy),
                    style = MaterialTheme.typography.bodyMedium
                )
                Checkbox(checked = isActive, onCheckedChange = { isActive = it })
            }

            Row(
                Modifier.padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.is_requiring_invoice),
                    style = MaterialTheme.typography.bodyMedium
                )
                Checkbox(
                    checked = isRequiringInvoice,
                    onCheckedChange = { isRequiringInvoice = it })

            }

            if (isProgressBarActive)
                CircularProgressIndicator()


            ButtonCustom(modifier = Modifier.padding(vertical = 60.dp), onClick = {
                onClickConfirmButton.invoke(
                    CreatePatientDto(
                        firstName.lowercase().replaceFirstChar(Char::uppercase),
                        lastName.lowercase().replaceFirstChar(Char::uppercase) ,
                        phone,
                        email,
                        numberSS,
                        address,
                        postalCode,
                        city,
                        age,
                        income,
                        isActive,
                        isRequiringInvoice
                    )
                )
            })
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CreatePatientsPreview() {
PsyBudgetComposeTheme(dynamicColor = false) {
    CreatePatientView(false) {}
}
}
