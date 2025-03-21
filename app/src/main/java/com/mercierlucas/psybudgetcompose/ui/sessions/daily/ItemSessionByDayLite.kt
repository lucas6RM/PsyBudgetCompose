package com.mercierlucas.psybudgetcompose.ui.sessions.daily

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mercierlucas.psybudgetcompose.R
import com.mercierlucas.psybudgetcompose.data.custom.SessionByDayLite
import com.mercierlucas.psybudgetcompose.utils.theme.MyAppleBlue
import com.mercierlucas.psybudgetcompose.utils.theme.MyBlue

@Composable
fun ItemSessionByDayLite(
    session: SessionByDayLite,
    index : Int,
    modifier: Modifier = Modifier,
    onClickInfoDetailed: (Long) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = with(modifier){
            fillMaxWidth()
            padding(10.dp)
        }
    )
    {

        Column(
            modifier = Modifier.weight(1F),
            horizontalAlignment = Alignment.Start) {
            Text(
                text = stringResource(id = R.string.session_number_d, index.toString()),
                maxLines = 1,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(10.dp)
            )
        }

        Column(modifier = Modifier.weight(0.7F),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = session.patientFirstName,
                style = MaterialTheme.typography.labelSmall

                )
        }

        Column(modifier = Modifier.weight(0.7F),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = session.patientLastNAme,
                style = MaterialTheme.typography.labelSmall
            )
        }

        Column(modifier = Modifier.weight(1F),
            horizontalAlignment = Alignment.End) {

            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = stringResource(id = R.string.session_informations_detailed),
                modifier
                    .size(30.dp)
                    .clickable {
                        onClickInfoDetailed.invoke(session.sessionId)
                    },
                //tint = MyAppleBlue
            )
        }
    }


}

