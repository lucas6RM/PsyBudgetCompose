package com.mercierlucas.psybudgetcompose.ui.sessions.daily


import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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

import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mercierlucas.feedarticles.Utils.showToast
import com.mercierlucas.feedarticlesjetpack.utils.convertMillisToDate
import com.mercierlucas.psybudgetcompose.R
import com.mercierlucas.psybudgetcompose.data.custom.SessionByDayLite
import com.mercierlucas.psybudgetcompose.data.custom.SessionsByDayStats
import com.mercierlucas.psybudgetcompose.ui.custom.HeaderCustom
import com.mercierlucas.psybudgetcompose.ui.custom.SplitLine
import com.mercierlucas.psybudgetcompose.ui.custom.datepickers.DatePickerDocked
import com.mercierlucas.psybudgetcompose.navigation.Screen
import com.mercierlucas.psybudgetcompose.utils.theme.MyGreen

import com.mercierlucas.psybudgetcompose.utils.theme.PsyBudgetComposeTheme
import java.util.Calendar


@Composable
fun DailySessionsScreen(
    navController: NavHostController,
    dailySessionViewModel: DailySessionViewModel
) {
    val isProgressBarActive by dailySessionViewModel.isProgressBarDisplayedStateFlow
        .collectAsState()
    val sessionsByDay by dailySessionViewModel.sessionsByDayStateFlow.collectAsState()
    val sessionsByDayStats by dailySessionViewModel.sessionsByDayStatsStateFlow.collectAsState()

    val context = LocalContext.current


    DailySessionsView(
        onClickToCreateSession = {
        navController.navigate(Screen.CreateSession.route){
            popUpTo(Screen.DailySessions.route) { inclusive = true }
        }
    },
        onDateSelected = {selectedDate ->
            dailySessionViewModel.getSessionsByDay(selectedDate)
        },
        sessionsByDay,
        sessionsByDayStats,
        onClickInfoDetailed = {sessionId ->
            navController.navigate(Screen.DetailsSession.route + "/$sessionId")
        }
    )

    LaunchedEffect(true) {
        dailySessionViewModel.messageSharedFlow.collect { messageId ->
            with(context){
                showToast(getString(messageId))
            }
        }
    }

    LaunchedEffect (true){
        val currentDateFormatted = convertMillisToDate(Calendar.getInstance().timeInMillis)
        dailySessionViewModel.getSessionsByDay(currentDateFormatted)
    }

}


@Composable
fun DailySessionsView(
    onClickToCreateSession: () -> Unit,
    onDateSelected: (String) -> Unit,
    sessionsByDay: List<SessionByDayLite>,
    sessionsByDayStats: SessionsByDayStats,
    onClickInfoDetailed: (Long) -> Unit
    ) {

    Box {
        Column(Modifier.fillMaxSize()) {

            HeaderCustom(title = stringResource(id = R.string.daily_sessions))


            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DatePickerDocked(
                    modifier = Modifier.padding(vertical = 10.dp),
                    textLabel = stringResource(id = R.string.session_date),
                    onDateSelected = { selectedDate ->
                        if (selectedDate != null) {
                            onDateSelected.invoke(selectedDate)
                        }
                    }
                )
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(id = R.string.create_session),
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            onClickToCreateSession.invoke()
                        }

                )

            }

            SplitLine()

            Column (Modifier.fillMaxSize()){

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 10.dp)
                ) {
                    var index = 1
                    items(
                        items = sessionsByDay,
                    ) { session ->
                        Card(
                            modifier = Modifier
                                .padding(5.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer),
                            elevation = CardDefaults.cardElevation( defaultElevation = 2.dp)
                        ) {
                            ItemSessionByDayLite(
                                session = session,
                                index = index,
                                onClickInfoDetailed = onClickInfoDetailed)
                            Log.i(TAG, index.toString())
                            index++
                        }
                    }
                }

                SplitLine()

                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(MyGreen)
                        .padding(vertical = 20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = stringResource(
                                id = R.string.number_of_sessions_completed_this_day_s,
                                sessionsByDayStats.numberOfCompletedSessions
                            ),
                            style = MaterialTheme.typography.labelSmall
                        )
                        Spacer(Modifier.padding(vertical = 10.dp))
                        Text(
                            text = stringResource(
                                id = R.string.total_amount_paid_and_validated_s,
                                sessionsByDayStats.totalAmountPaid
                            ),
                            style = MaterialTheme.typography.labelSmall
                        )
                        Spacer(Modifier.padding(vertical = 10.dp))
                        Text(
                            text = stringResource(
                                id = R.string.total_amount_due_to_be_validated_s,
                                sessionsByDayStats.totalAmountToBeValidated
                            ),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DailySessionsPreview(){

    PsyBudgetComposeTheme (dynamicColor = false){
        DailySessionsView({},{}, emptyList(), SessionsByDayStats(3,3,3),{})
    }

}