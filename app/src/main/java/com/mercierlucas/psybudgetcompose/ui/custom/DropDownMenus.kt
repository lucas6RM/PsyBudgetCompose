package com.mercierlucas.psybudgetcompose.ui.custom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mercierlucas.psybudgetcompose.data.model.PatientLite
import com.mercierlucas.psybudgetcompose.utils.PaymentMethod
import kotlin.enums.EnumEntries

@Composable
fun DropDownPatientsInTherapyList(
    titleList: String = "List",
    patientsInTherapy : List<PatientLite>,
    onClickPatientSelected : (PatientLite) -> Unit,
    onPatientChange : () ->Unit
) {


    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    val itemPosition = remember {
        mutableStateOf(-1)
    }

    Row (
        Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceEvenly){

        Text(titleList)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    isDropDownExpanded.value = true
                }
            ) {
                if (itemPosition.value < 0)
                    Text(text = "Select a patient")
                else
                    Text(text = "${patientsInTherapy[itemPosition.value].firstName} " +
                        patientsInTherapy[itemPosition.value].lastName)


                if(!isDropDownExpanded.value)
                    Icon(
                        imageVector = Icons.Rounded.ArrowDropDown,
                        contentDescription = "Arrow Drop Down Menu"
                    )
                else
                    Icon(
                        imageVector = Icons.Rounded.ArrowDropUp,
                        contentDescription = "Arrow Drop Down Menu"
                    )
            }
            DropdownMenu(
                expanded = isDropDownExpanded.value,
                onDismissRequest = {
                    isDropDownExpanded.value = false
                }) {

                patientsInTherapy.forEachIndexed { index, patient ->
                    DropdownMenuItem(text = {
                        Text(text = "${patient.firstName} ${patient.lastName}")
                    },
                        onClick = {
                            isDropDownExpanded.value = false
                            itemPosition.value = index
                            onClickPatientSelected.invoke(patient)
                            onPatientChange.invoke()
                        })
                }
            }
        }
    }
}

@Composable
fun DropDownEnumString(
    titleList: String = "List",
    entries: EnumEntries<PaymentMethod>,
    onPaymentMethodSelected: (String) -> Unit
) {


    val strEnumList = entries.map { it.str }

    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    val itemPosition = remember {
        mutableStateOf(0)
    }

    Row (
        Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceEvenly){

        Text(titleList)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    isDropDownExpanded.value = true
                }
            ) {
                Text(strEnumList[itemPosition.value])


                if(!isDropDownExpanded.value)
                    Icon(
                        imageVector = Icons.Rounded.ArrowDropDown,
                        contentDescription = "Arrow Drop Down Menu"
                    )
                else
                    Icon(
                        imageVector = Icons.Rounded.ArrowDropUp,
                        contentDescription = "Arrow Drop Down Menu"
                    )
            }
            DropdownMenu(
                expanded = isDropDownExpanded.value,
                onDismissRequest = {
                    isDropDownExpanded.value = false
                }) {

                strEnumList.forEachIndexed { index, username ->
                    DropdownMenuItem(text = {
                        Text(text = username)
                    },
                        onClick = {
                            isDropDownExpanded.value = false
                            itemPosition.value = index
                            onPaymentMethodSelected.invoke(strEnumList[index])
                        })
                }
            }
        }
    }
}


