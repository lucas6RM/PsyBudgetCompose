package com.mercierlucas.psybudgetcompose.ui.patients.all

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mercierlucas.feedarticles.Utils.showToast
import com.mercierlucas.psybudgetcompose.data.model.PatientLite
import com.mercierlucas.psybudgetcompose.ui.custom.HeaderCustom
import com.mercierlucas.psybudgetcompose.ui.custom.OutlinedTextFieldCustom
import com.mercierlucas.psybudgetcompose.ui.custom.SplitLine
import com.mercierlucas.psybudgetcompose.ui.navigation.Screen
import com.mercierlucas.psybudgetcompose.utils.theme.MyBlue
import com.mercierlucas.psybudgetcompose.utils.theme.MyGreen


@Composable
fun AllPatientsScreen(
    navController: NavHostController,
    allPatientsViewModel: AllPatientsViewModel
) {
    val patientsList by allPatientsViewModel.patientLiteListFilteredStateFlow.collectAsState()
    val isFilterCheckedState by allPatientsViewModel.isFilterCheckedStateFlow.collectAsState()
    val context = LocalContext.current


    AllPatientsView(
        patientsList,
        isFilterCheckedState,
        onClickAddButton = { navController.navigate(Screen.CreatePatient.route) },
        onCheckInTherapyFilter = { checkedStatus ->
                allPatientsViewModel.setIsFilterChecked(checkedStatus)
        })

    LaunchedEffect(true) {
        allPatientsViewModel.messageSharedFlow.collect { message ->
            context.showToast(message)
        }
    }
}

@Composable
fun AllPatientsView(
    patientsList: List<PatientLite>,
    isFilterChecked: Boolean,
    onClickAddButton: () -> Unit,
    onCheckInTherapyFilter: (Boolean) -> Unit
){

    var searchPatient by rememberSaveable {
        mutableStateOf("search")
    }

    Column (modifier = Modifier.fillMaxSize()){
        HeaderCustom("All Patients directory")


        Row (
            Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            ){
            OutlinedTextFieldCustom(
                value = searchPatient,
                onValueChange = {searchPatient = it}, labelText = "Search a patient",
            )

            Column (
                modifier = Modifier.weight(1F),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add a patient",
                    modifier = Modifier
                        .size(50.dp)
                        .clickable(onClick = { onClickAddButton.invoke() })
                )
            }
        }

        TopRowTable(
            Modifier
                .background(MyBlue),isFilterChecked,onCheckInTherapyFilter)


        LazyColumn(modifier = Modifier
            .weight(1f)
            .padding(top = 10.dp)) {
            items(items = patientsList){
                Card (modifier = Modifier.padding(5.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    elevation = CardDefaults.cardElevation( defaultElevation = 2.dp)
                ){
                    ItemPatientLite(patient = it)
                }
            }
        }

        SplitLine()

        Row (
            Modifier
                .fillMaxWidth()
                .background(MyGreen)
                .padding(vertical = 20.dp)
                ,
            horizontalArrangement = Arrangement.Center){
            Column (horizontalAlignment = Alignment.Start){
            Text(text = "Total amount of patients in therapy : " +
                    "${patientsList.filter { it.isActive }.size}",
                fontSize = 18.sp)
                Spacer(Modifier.padding(vertical = 10.dp))
            Text(text = "Total amount of patients : ${patientsList.size}",
                fontSize = 18.sp)
            }
        }

    }
}


@Composable
fun TopRowTable(modifier: Modifier = Modifier, isFilterChecked: Boolean, onCheckInTherapyFilter: (Boolean) -> Unit){

    var isFilterCheckedState = remember {
        mutableStateOf(isFilterChecked)
    }

    SplitLine()

    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = with(modifier){
            fillMaxWidth()
            padding(horizontal = 5.dp)
        }
    )
    {
        Column(modifier = Modifier.weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Lastname",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp)
            )
        }

        Column(modifier = Modifier.weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Firstname",
                fontSize = 18.sp,
                modifier = Modifier.padding(10.dp)
            )
        }
        Column(modifier = Modifier.weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Column(modifier = Modifier.padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "in Therapy ?",
                    fontSize = 15.sp,
                )
                Checkbox(
                    checked = isFilterCheckedState.value,
                    onCheckedChange = {
                        isFilterCheckedState.value = it
                        onCheckInTherapyFilter.invoke(it)
                    }
                )
            }
        }
    }
    SplitLine()
}

@Composable
fun ItemPatientLite(patient: PatientLite,modifier: Modifier = Modifier){

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = with(modifier){
            padding(5.dp)
            fillMaxWidth()
        }
    )
    {

        Column(modifier = Modifier.weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = patient.lastName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }

        Column(modifier = Modifier.weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = patient.firstName,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }

        Column(modifier = Modifier.weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Checkbox(
                checked = patient.isActive,
                onCheckedChange = { },
                enabled = false,
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AllPatientsPreview() {
    AllPatientsView(
        listOf(
        PatientLite(0,"lucasdsqsdqsd","mercier",true),
        PatientLite(1,"tonton","antoine", false)
    ), false,{}) {}

}


