package com.mercierlucas.psybudgetcompose.ui.patients.one

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Switch
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import androidx.navigation.NavHostController
import com.mercierlucas.feedarticles.Utils.showToast
import com.mercierlucas.psybudgetcompose.R
import com.mercierlucas.psybudgetcompose.data.network.dtos.PatientDto
import com.mercierlucas.psybudgetcompose.data.network.dtos.UpdatePatientDto
import com.mercierlucas.psybudgetcompose.ui.custom.ButtonCustom
import com.mercierlucas.psybudgetcompose.ui.custom.HeaderCustom
import com.mercierlucas.psybudgetcompose.ui.custom.OutlinedTextFieldCustom
import com.mercierlucas.psybudgetcompose.ui.custom.RadioButtonSingleSelectionHorizontal
import com.mercierlucas.psybudgetcompose.utils.theme.PsyBudgetComposeTheme

@Composable
fun PatientByIdScreen(
    navController: NavHostController,
    patientByIdViewModel: PatientByIdViewModel,
    idPatientSelected: Long
) {
    patientByIdViewModel.setPatientInfos(idPatientSelected)
    val patientSelected by patientByIdViewModel.patientSelectedStateFlow.collectAsState()

    val context = LocalContext.current

    patientSelected?.let { patientIdentified ->
        PatientByIdView(
            patientIdentified,
            onDeleteClick = { idPatientToDelete ->
                            patientByIdViewModel.deletePatientById(idPatientToDelete)
            },
            onModifyClicked = { patientToUpdate ->
                              patientByIdViewModel.validateInputs(patientToUpdate)

            },
        )
    }


    LaunchedEffect(true) {
        patientByIdViewModel.messageSharedFlow.collect { messageId ->
            with(context){
                showToast(getString(messageId))
            }
        }
    }


    LaunchedEffect(true) {
        patientByIdViewModel.goToAllPatientScreenSharedFlow.collect { yes ->
            if(yes)
                navController.popBackStack()
        }
    }

}

@Composable
fun PatientByIdView(
    patientIdentified: PatientDto,
    onDeleteClick : (Long) -> Unit,
    onModifyClicked : (UpdatePatientDto) -> Unit
) {

    var firstName          by remember { mutableStateOf(patientIdentified.firstName) }
    var lastName           by remember { mutableStateOf(patientIdentified.lastName) }
    var phone              by remember { mutableStateOf(patientIdentified.phoneNumber) }
    var email              by remember { mutableStateOf(patientIdentified.email) }
    var numberSS           by remember { mutableStateOf(patientIdentified.numSS) }
    var address            by remember { mutableStateOf(patientIdentified.address) }
    var postalCode         by remember { mutableStateOf(patientIdentified.postalCode) }
    var city               by remember { mutableStateOf(patientIdentified.city) }
    var age                by remember { mutableStateOf(patientIdentified.age) }
    var income             by remember { mutableStateOf(patientIdentified.income) }
    var isActive           by remember { mutableStateOf(patientIdentified.isActive) }
    var isRequiringInvoice by remember { mutableStateOf(patientIdentified.isRequiringInvoice) }

    var isModifyClicked    by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HeaderCustom(if (isModifyClicked) "Patient Details" else "Modify patient")

        Row (
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){

            Text(text = "Modify :")
            Spacer(modifier = Modifier.padding(horizontal = 10.dp))
            Switch(
                checked = isModifyClicked,
                onCheckedChange = {
                    isModifyClicked = it
                }
            )

        }


        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 50.dp)
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextFieldCustom(
                value = firstName,
                onValueChange = { firstName = it },
                labelText = "First name (*)",
                enabled = isModifyClicked
            )
            OutlinedTextFieldCustom(
                value = lastName,
                onValueChange = { lastName = it },
                labelText = "Last name (*)",
                enabled = isModifyClicked
            )
            phone?.let {
                OutlinedTextFieldCustom(
                    value = it,
                    onValueChange = { phone = it },
                    labelText = "Phone number (*)",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    enabled = isModifyClicked
                )
            }

            OutlinedTextFieldCustom(
                value = email ?: "",
                onValueChange = { email = it },
                labelText = "Email",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                enabled = isModifyClicked
            )


            OutlinedTextFieldCustom(
                value = numberSS?.toString() ?: "" ,
                onValueChange = { numberSS = it.toInt() },
                labelText = "n° Social Security",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                enabled = isModifyClicked
            )

            OutlinedTextFieldCustom(
                value = address ?: "",
                onValueChange = { address = it },
                labelText = "Address",
                enabled = isModifyClicked
            )

            OutlinedTextFieldCustom(
                value = postalCode?.toString() ?: "",
                onValueChange = { postalCode = it.toInt() },
                labelText = "Postal code",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                enabled = isModifyClicked
            )
            OutlinedTextFieldCustom(
                value = city ?: "",
                onValueChange = { city = it },
                labelText = "City",
                enabled = isModifyClicked
            )
            OutlinedTextFieldCustom(
                value = age?.toString() ?: "",
                onValueChange = { age = it.toInt() },
                labelText = "Postal code",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                enabled = isModifyClicked
            )

            Row(
                Modifier.padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Income : ", fontSize = 18.sp)
                RadioButtonSingleSelectionHorizontal(
                    radioOptions = listOf("Low", "Mid", "High"),
                    callbackRBSelected = { radioButtonSelected ->
                        income = radioButtonSelected.lowercase()
                    },
                    enabled = isModifyClicked
                )
            }

            Row(
                Modifier.padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Is in therapy : ", fontSize = 18.sp)
                Checkbox(
                    checked = isActive,
                    onCheckedChange = { isActive = it },
                    enabled = isModifyClicked
                )
            }

            Row(
                Modifier.padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Is Requiring Invoice : ", fontSize = 18.sp)
                Checkbox(
                    checked = isRequiringInvoice,
                    onCheckedChange = { isRequiringInvoice = it },
                    enabled = isModifyClicked)

            }



                Row (verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ){

                    if(isModifyClicked)
                        ButtonCustom(
                            text = stringResource(id = R.string.delete_patient),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red),
                            onClick = {
                                onDeleteClick.invoke(patientIdentified.id)
                            }
                        )

                    if(isModifyClicked)
                        ButtonCustom(
                            text = stringResource(id = R.string.modify_patient),
                            onClick = {
                                onModifyClicked.invoke(UpdatePatientDto(
                                    patientIdentified.id,
                                    firstName,
                                    lastName,
                                    phone ?: "boo",
                                    email,
                                    numberSS,
                                    address,
                                    postalCode,
                                    city,
                                    age,
                                    income ?: "Mid",
                                    isActive,
                                    isRequiringInvoice
                                ))
                            }
                        )
                }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PatientByIdPreview(){
    PsyBudgetComposeTheme (darkTheme = false,dynamicColor = false){
        PatientByIdView(
            PatientDto(
            null,
            null,
            null,
            email = null,
            firstName = "toto",
            123,
            "Low",
            true,
            true,
            "smith",
            0,
            "988997489",
            13009
            ),
            {},{}
        )
    }
}